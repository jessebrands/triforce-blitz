package com.triforceblitz.triforceblitz.seeds.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triforceblitz.triforceblitz.python.Interpreter;
import com.triforceblitz.triforceblitz.randomizer.Randomizer;
import com.triforceblitz.triforceblitz.randomizer.RandomizerSettings;
import com.triforceblitz.triforceblitz.seeds.Seed;
import com.triforceblitz.triforceblitz.seeds.generator.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class GenerateSeedTask implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(GenerateSeedTask.class);

    private static final DateTimeFormatter filenameTimestampFormatter = DateTimeFormatter
            .ofPattern("yyyyMMdd'T'hhmmss")
            .withZone(ZoneOffset.UTC);

    private final Interpreter interpreter;
    private final Randomizer randomizer;
    private final Seed seed;
    private final Path romFile;
    private final Path outputDirectory;
    private final ApplicationEventPublisher eventPublisher;

    public GenerateSeedTask(Interpreter interpreter, Randomizer randomizer, Seed seed, Path romFile, Path outputDirectory, ApplicationEventPublisher eventPublisher) {
        this.interpreter = interpreter;
        this.randomizer = randomizer;
        this.seed = seed;
        this.romFile = romFile;
        this.outputDirectory = outputDirectory;
        this.eventPublisher = eventPublisher;
    }

    private void createOutputDirectory() {
        try {
            log.info("Creating output directory {}", outputDirectory);
            Files.createDirectories(outputDirectory);
        } catch (IOException e) {
            log.error("Failed to create output directory, please check read/write permissions");
            eventPublisher.publishEvent(new GeneratorFailedEvent(seed, e));
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
            eventPublisher.publishEvent(new GeneratorFailedEvent(seed, e));
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
        // Get a handle towards the log file.
        var logFilename = String.format("TriforceBlitz_Randomizer_%s.log",
                filenameTimestampFormatter.format(Instant.now()));
        var logFile = outputDirectory.resolve(logFilename).toFile();
        // Open the log file and begin generating.
        try (var logFileWriter = new FileWriter(logFile);
             var logWriter = new BufferedWriter(logFileWriter)) {
            log.info("Running Ocarina of Time randomizer");
            var process = processBuilder.start();
            try (var inStream = process.getErrorStream();
                 var in = new InputStreamReader(inStream);
                 var reader = new BufferedReader(in)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logWriter.write(line);
                    logWriter.newLine();
                    eventPublisher.publishEvent(new GeneratorLogEvent(seed, line));
                }
            }
        } catch (IOException e) {
            log.error("Got error while running Ocarina of Time Randomizer: {}", e.getMessage());
            eventPublisher.publishEvent(new GeneratorFailedEvent(seed, e));
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void run() {
        var start = Instant.now();
        log.info("Seed generation started for seed {} at {}", seed.getId(), start);
        eventPublisher.publishEvent(new GeneratorStartedEvent(seed));
        createOutputDirectory();
        var settingsFilename = outputDirectory.resolve(RandomizerSettings.FILENAME);
        createSettingsFile(settingsFilename);
        generateSeed(settingsFilename);
        var elapsed = Duration.between(start, Instant.now());
        log.info("Seed generation finished in {} for seed {}", elapsed, seed.getId());
        eventPublisher.publishEvent(new GeneratorFinishedEvent(seed));
    }
}
