package com.triforceblitz.triforceblitz.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triforceblitz.triforceblitz.python.PythonService;
import com.triforceblitz.triforceblitz.seeds.Seed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocalGeneratorService implements GeneratorService {
    private final static Logger logger = LoggerFactory.getLogger(LocalGeneratorService.class);

    private final GeneratorConfig config;
    private final PythonService pythonService;
    private final ObjectMapper objectMapper;

    public LocalGeneratorService(GeneratorConfig config, PythonService pythonService, ObjectMapper objectMapper) {
        this.config = config;
        this.pythonService = pythonService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<Generator> findGenerator(String version) {
        var installPath = config.getGeneratorsPath().resolve(version);
        var entryFile = installPath.resolve("OoTRandomizer.py");
        if (Files.exists(entryFile) && !Files.isDirectory(entryFile)) {
            return Optional.of(new LocalGenerator(installPath));
        }
        return Optional.empty();
    }

    @Override
    public Seed generateSeed(String version, String seed) throws Exception {
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
        // TODO: The season should be configurable.
        var process = generator.generateSeed(interpreter, settingsPath, "Triforce Blitz S2");

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
}
