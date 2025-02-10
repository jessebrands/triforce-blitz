package com.triforceblitz.triforceblitz.seeds.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triforceblitz.triforceblitz.python.Interpreter;
import com.triforceblitz.triforceblitz.randomizer.Randomizer;
import com.triforceblitz.triforceblitz.randomizer.RandomizerSettings;
import com.triforceblitz.triforceblitz.seeds.Seed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

public class GenerateSeedTask implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(GenerateSeedTask.class);

    private final Interpreter interpreter;
    private final Randomizer randomizer;
    private final Seed seed;
    private final Path romFile;
    private final Path outputDirectory;

    public GenerateSeedTask(Interpreter interpreter, Randomizer randomizer, Seed seed, Path romFile, Path outputDirectory) {
        this.interpreter = interpreter;
        this.randomizer = randomizer;
        this.seed = seed;
        this.romFile = romFile;
        this.outputDirectory = outputDirectory;
    }

    private void createOutputDirectory() {
        try {
            log.info("Creating output directory {}", outputDirectory);
            Files.createDirectories(outputDirectory);
        } catch (IOException e) {
            log.error("Failed to create output directory, please check read/write permissions");
            throw new UncheckedIOException(e);
        }
    }

    private void createSettingsFile(Path settingsFilename) {
        try {
            log.info("Creating settings file: {}", settingsFilename);
            var settings = new RandomizerSettings(romFile, outputDirectory);
            new ObjectMapper().writeValue(settingsFilename.toFile(), settings);
        } catch (IOException e) {
            log.error("Could not create settings file");
            throw new UncheckedIOException(e);
        }
    }

    private void generateSeed(Path settingsFilename) {
        var processBuilder = randomizer.generate(
                interpreter,
                seed.getSeed(),
                seed.getPreset(),
                settingsFilename
        );
        try {
            log.info("Running Ocarina of Time randomizer");
            var process = processBuilder.start();
            try (var inStream = process.getErrorStream();
                 var in = new InputStreamReader(inStream);
                 var reader = new BufferedReader(in)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.info("==> {}", line);
                }
            }
            seed.setGeneratedAt(Instant.now());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void run() {
        var start = Instant.now();
        log.info("Seed generation started for seed {} at {}", seed.getId(), start);
        createOutputDirectory();
        var settingsFilename = outputDirectory.resolve(RandomizerSettings.FILENAME);
        createSettingsFile(settingsFilename);
        generateSeed(settingsFilename);
        var elapsed = Duration.between(start, Instant.now());
        log.info("Seed generation finished in {} for seed {}", elapsed, seed.getId());
    }
}
