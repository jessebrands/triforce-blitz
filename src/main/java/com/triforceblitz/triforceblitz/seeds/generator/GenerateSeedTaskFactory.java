package com.triforceblitz.triforceblitz.seeds.generator;

import com.triforceblitz.triforceblitz.TriforceBlitzConfig;
import com.triforceblitz.triforceblitz.python.PythonService;
import com.triforceblitz.triforceblitz.randomizer.RandomizerService;
import com.triforceblitz.triforceblitz.seeds.Seed;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class GenerateSeedTaskFactory {
    private final PythonService pythonService;
    private final RandomizerService randomizerService;
    private final TriforceBlitzConfig config;
    private final ApplicationEventPublisher eventPublisher;

    public GenerateSeedTaskFactory(PythonService pythonService,
                                   RandomizerService randomizerService,
                                   TriforceBlitzConfig config,
                                   ApplicationEventPublisher eventPublisher) {
        this.pythonService = pythonService;
        this.randomizerService = randomizerService;
        this.config = config;
        this.eventPublisher = eventPublisher;
    }

    GenerateSeedTask create(Seed seed) {
        var interpreter = pythonService.findInterpreter();
        var randomizer = randomizerService.getRandomizer(seed.getRandomizerVersion());
        return new GenerateSeedTask(
                interpreter,
                randomizer,
                seed,
                config.getRomFile(),
                config.getSeedStoragePath().resolve(seed.getId().toString()),
                eventPublisher
        );
    }
}
