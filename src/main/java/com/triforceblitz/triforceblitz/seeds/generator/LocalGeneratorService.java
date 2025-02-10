package com.triforceblitz.triforceblitz.seeds.generator;

import com.triforceblitz.triforceblitz.TriforceBlitzConfig;
import com.triforceblitz.triforceblitz.python.PythonService;
import com.triforceblitz.triforceblitz.randomizer.RandomizerService;
import com.triforceblitz.triforceblitz.seeds.Seed;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class LocalGeneratorService implements GeneratorService {
    private final PythonService pythonService;
    private final RandomizerService randomizerService;
    private final TriforceBlitzConfig config;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public LocalGeneratorService(PythonService pythonService,
                                 RandomizerService randomizerService,
                                 TriforceBlitzConfig config) {
        this.pythonService = pythonService;
        this.randomizerService = randomizerService;
        this.config = config;
    }

    @Override
    public Seed generateSeed(String version, String randomizerSeed, String preset) {
        var interpreter = pythonService.findInterpreter();
        var randomizer = randomizerService.getRandomizer(version);
        var seed = new Seed(randomizerSeed, preset);
        executor.submit(new GenerateSeedTask(
                interpreter,
                randomizer,
                seed,
                config.getRomFile(),
                config.getSeedStoragePath().resolve(seed.getId())
        ));
        return seed;
    }
}
