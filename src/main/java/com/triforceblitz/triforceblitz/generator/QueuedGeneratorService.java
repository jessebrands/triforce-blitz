package com.triforceblitz.triforceblitz.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triforceblitz.triforceblitz.Version;
import com.triforceblitz.triforceblitz.python.PythonService;
import com.triforceblitz.triforceblitz.seeds.Season;
import com.triforceblitz.triforceblitz.seeds.SeasonRepository;
import com.triforceblitz.triforceblitz.seeds.Seed;
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
    private final SeasonRepository seasonRepository;
    private final ObjectMapper objectMapper;

    private final List<SeasonRequirements> seasonRequirements = new ArrayList<>();

    public QueuedGeneratorService(ApplicationEventPublisher eventPublisher,
                                  GeneratorConfig config,
                                  PythonService pythonService,
                                  SeasonRepository seasonRepository,
                                  ObjectMapper objectMapper) {
        this.eventPublisher = eventPublisher;
        this.config = config;
        this.pythonService = pythonService;
        this.seasonRepository = seasonRepository;
        this.objectMapper = objectMapper;

        // Create some fake requirements for now!
        seasonRequirements.addAll(List.of(
                new SeasonRequirements(
                        seasonRepository.save(new Season(1, "1", "Triforce Blitz")),
                        List.of("blitz"),                     // Valid branches
                        Version.from("v6.2.0-blitz-0.1")      // Minimum version
                ),
                new SeasonRequirements(
                        seasonRepository.save(new Season(2, "2", "Triforce Blitz S2")),
                        List.of("blitz"),                        // Valid branches
                        Version.from("v7.1.3-blitz-0.40")        // Minimum version
                )
        ));
    }

    @Override
    public Optional<Generator> findGenerator(Version version) {
        var installPath = config.getGeneratorsPath().resolve(version.toString());
        var entryFile = installPath.resolve("OoTRandomizer.py");
        if (Files.exists(entryFile) && !Files.isDirectory(entryFile)) {
            return Optional.of(new LocalGenerator(installPath));
        }
        return Optional.empty();
    }

    @Override
    public Seed generateSeed(Version version, Season season, String seed) throws Exception {
        if (!isSeasonCompatible(version, season)) {
            throw new RuntimeException("season incompatible with version");
        }
        // TODO: Add a more specific exception.
        var generator = findGenerator(version).orElseThrow();
        var interpreter = pythonService.findInterpreter().orElseThrow();
        var uuid = UUID.randomUUID();
        var outputPath = config.getSeedsPath().resolve(uuid.toString());
        var settings = new GeneratorSettings(config.getRom(), outputPath, seed);

        // Send off our task to the executor.
        logger.info("Handing off seed generation task to the executor.");
        executor.execute(new GeneratorTask(interpreter, generator, season.getPreset(), settings, eventPublisher));
        return new Seed();
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
        return seasonRequirements.stream()
                .filter(req -> req.satisfiesRequirements(version))
                .map(SeasonRequirements::getSeason)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isSeasonCompatible(Version version, Season season) {
        return getCompatibleSeasons(version).contains(season);
    }
}
