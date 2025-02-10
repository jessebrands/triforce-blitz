package com.triforceblitz.triforceblitz.seeds;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triforceblitz.triforceblitz.randomizer.RandomizerSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.UUID;

@Service
public class LocalSeedService implements SeedService {
    private static final Logger logger = LoggerFactory.getLogger(LocalSeedService.class);

    @Override
    public Seed generateSeed() {
        var seed = new Seed(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        // 1. Find a Python interpreter
        var interpreter = Path.of("C:\\Users\\Jesse\\AppData\\Local\\Programs\\Python\\Python313\\python.exe");
        // 2. Locate the Randomizer
        var randomizer = Path.of("C:\\Users\\Jesse\\AppData\\Local\\triforceblitz\\generators\\8.1.37-blitz-0.59\\OoTRandomizer.py");
        // 3. Generate a seed string to pass to the randomizer
        var outputDirectory = Path.of("C:\\Users\\Jesse\\AppData\\Roaming\\triforceblitz\\seeds").resolve(seed.getId());
        var settingsFilename = outputDirectory.resolve(RandomizerSettings.FILENAME);
        var romFilename = Path.of("C:\\Users\\Jesse\\AppData\\Roaming\\triforceblitz\\ZOOTDEC.z64");
        try {
            // 4. Create output directory.
            Files.createDirectories(outputDirectory);
            // 5. Create the settings file
            var settings = new RandomizerSettings(romFilename, outputDirectory);
            new ObjectMapper().writeValue(settingsFilename.toFile(), settings);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        // 6. Invoke the randomizer
        var processBuilder = new ProcessBuilder(
                interpreter.toAbsolutePath().toString(),
                randomizer.toAbsolutePath().toString(),
                "--seed", seed.getSeed(),
                "--settings", settingsFilename.toAbsolutePath().toString(),
                "--settings_preset", "Triforce Blitz",
                "--no_log"
        );
        try {
            var process = processBuilder.start();
            // 7. Report the output to the log
            try (var inStream = process.getErrorStream();
                 var in = new InputStreamReader(inStream);
                 var reader = new BufferedReader(in)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logger.info("Randomizer ==> {}", line);
                }
            }
            seed.setGeneratedAt(Instant.now());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        // 8. Return the generated seed
        return seed;
    }
}
