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
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LocalGeneratorService implements GeneratorService {
    private final static Logger logger = LoggerFactory.getLogger(LocalGeneratorService.class);

    private final GeneratorConfig config;
    private final PythonService pythonService;
    private final SeasonRepository seasonRepository;
    private final ObjectMapper objectMapper;

    private final List<SeasonRequirements> seasonRequirements = new ArrayList<>();

    public LocalGeneratorService(GeneratorConfig config,
                                 PythonService pythonService,
                                 SeasonRepository seasonRepository,
                                 ObjectMapper objectMapper) {
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
                        seasonRepository.save(new Season(2, "SGL 2022", "Triforce Blitz")), // Season
                        List.of("sgl2022"),                          // Valid branches
                        Version.from("v6.2.158-sgl2022-0.1"),        // Minimum version
                        Version.from("v6.2.228-sgl2022-0.17")        // Maximum version
                ),
                new SeasonRequirements(
                        seasonRepository.save(new Season(3, "2", "Triforce Blitz S2")),
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

        // Create output directory and write out the settings file.
        Files.createDirectories(outputPath);
        var settingsPath = outputPath.resolve("settings.json");
        var settings = new GeneratorSettings(config.getRom(), outputPath, seed);
        objectMapper.writeValue(settingsPath.toFile(), settings);

        // Invoke the generator.
        var process = generator.generateSeed(interpreter, settingsPath, season.getPreset());

        // Log the output for now. Note that the randomizer outputs to stderr only (lol)
        // TODO: Figure out a better way of redirecting output for later; we'll probably want to capture it
        //       for some kind of progress indicator on the frontend later.
        var stdin = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        var line = "";
        while ((line = stdin.readLine()) != null) {
            logger.info("OoTRandomizer: {}", line);
        }
        process.waitFor();

        // Open the spoiler log and retrieve data about the seed.
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
