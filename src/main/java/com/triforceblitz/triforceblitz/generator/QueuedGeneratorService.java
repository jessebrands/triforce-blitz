package com.triforceblitz.triforceblitz.generator;

import com.triforceblitz.triforceblitz.Version;
import com.triforceblitz.triforceblitz.python.PythonService;
import com.triforceblitz.triforceblitz.seeds.Season;
import com.triforceblitz.triforceblitz.seeds.SeasonRepository;
import com.triforceblitz.triforceblitz.seeds.Seed;
import com.triforceblitz.triforceblitz.seeds.SeedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class QueuedGeneratorService implements GeneratorService {
    private final static Logger logger = LoggerFactory.getLogger(QueuedGeneratorService.class);

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final ApplicationEventPublisher eventPublisher;
    private final GeneratorConfig config;
    private final PythonService pythonService;
    private final SeedRepository seedRepository;
    private final SeasonRequirementRepository requirementRepository;

    public QueuedGeneratorService(ApplicationEventPublisher eventPublisher,
                                  GeneratorConfig config,
                                  PythonService pythonService,
                                  SeasonRepository seasonRepository,
                                  SeedRepository seedRepository, SeasonRequirementRepository requirementRepository) {
        this.eventPublisher = eventPublisher;
        this.config = config;
        this.pythonService = pythonService;
        this.seedRepository = seedRepository;
        this.requirementRepository = requirementRepository;
    }

    @Override
    public Optional<Generator> getGenerator(Version version) {
        var installPath = config.getGeneratorsPath().resolve(version.toString());
        var entryFile = installPath.resolve("OoTRandomizer.py");
        if (Files.exists(entryFile) && !Files.isDirectory(entryFile)) {
            return Optional.of(new LocalGenerator(installPath));
        }
        return Optional.empty();
    }

    @Override
    public Seed generateSeed(Version version, Season season, String generatorSeed) throws Exception {
        if (!isSeasonCompatible(version, season)) {
            throw new RuntimeException("season incompatible with version");
        }

        var seed = Seed.create(version, season, generatorSeed);
        var generator = getGenerator(version).orElseThrow();
        var interpreter = pythonService.findInterpreter().orElseThrow();
        var outputPath = config.getSeedsPath().resolve(seed.getId().toString());
        var settings = new GeneratorSettings(config.getRom(), outputPath, generatorSeed);
        settings.setOutputName(seed.getId().toString());

        logger.info("Handing off seed generation task to the executor.");
        executor.execute(new GeneratorTask(interpreter, generator, seed, season.getPreset(), settings, eventPublisher));
        return seedRepository.save(seed);
    }

    @Override
    @Cacheable("available-generators")
    public Set<Version> getAvailableVersions() {
        var files = config.getGeneratorsPath().toFile().listFiles();
        if (files == null) {
            return Set.of();
        }

        return Stream.of(files)
                .filter(File::isDirectory)
                .map(File::getName)
                .map(Version::from)
                .filter(v -> !config.getBlacklistedBranches().contains(v.branch()))
                .collect(Collectors.toCollection(TreeSet::new))
                .reversed();
    }

    @Override
    public List<Season> getCompatibleSeasons(Version version) {
        return requirementRepository.findAll().stream()
                .filter(req -> req.satisfiesRequirements(version))
                .map(SeasonRequirement::getSeason)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isSeasonCompatible(Version version, Season season) {
        return getCompatibleSeasons(version).contains(season);
    }
}
