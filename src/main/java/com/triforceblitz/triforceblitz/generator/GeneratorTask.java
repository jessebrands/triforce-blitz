package com.triforceblitz.triforceblitz.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triforceblitz.triforceblitz.generator.events.GeneratorLogEvent;
import com.triforceblitz.triforceblitz.python.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * GeneratorTask is the actual work payload that does the actual work of generating a seed.
 *
 * @author Jesse
 */
public class GeneratorTask implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(GeneratorTask.class);

    private final PythonInterpreter interpreter;
    private final Generator generator;
    private final String preset;
    private final GeneratorSettings settings;
    private final ApplicationEventPublisher publisher;

    public GeneratorTask(PythonInterpreter interpreter, Generator generator, String preset,
                         GeneratorSettings settings,
                         ApplicationEventPublisher publisher) {
        this.interpreter = interpreter;
        this.generator = generator;
        this.preset = preset;
        this.settings = settings;
        this.publisher = publisher;
    }

    @Override
    public void run() {
        logger.info("Seed generation started!");
        var objectMapper = new ObjectMapper();
        var outputPath = Path.of(settings.getOutputPath());
        var settingsPath = outputPath.resolve("settings.json");

        try {
            logger.info("Creating output directory and settings file.");
            Files.createDirectories(Path.of(settings.getOutputPath()));
            objectMapper.writeValue(settingsPath.toFile(), settings);
        } catch (Exception e) {
            logger.error("Failed to create output directory: {}", e.getMessage());
            return;
        }

        try {
            logger.info("Starting generator process...");
            var process = generator.generateSeed(interpreter, settingsPath, preset);

            // OoTR logs to stderr only, so capture the error stream.
            var in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            var line = "";
            while ((line = in.readLine()) != null) {
                publisher.publishEvent(new GeneratorLogEvent(this, line));
            }

            var result = process.waitFor();
            if (result == 0) {
                logger.info("Seed generation finished successfully!");
            } else {
                logger.warn("Seed generation failed.");
            }
        } catch (Exception e) {
            logger.error("Failed to generate seed: {}", e.getMessage());
        }
    }
}
