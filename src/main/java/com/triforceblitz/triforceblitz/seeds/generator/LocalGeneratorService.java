package com.triforceblitz.triforceblitz.seeds.generator;

import com.triforceblitz.triforceblitz.TriforceBlitzConfig;
import com.triforceblitz.triforceblitz.python.PythonService;
import com.triforceblitz.triforceblitz.randomizer.RandomizerService;
import com.triforceblitz.triforceblitz.seeds.Seed;
import com.triforceblitz.triforceblitz.seeds.generator.events.GeneratorLogEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class LocalGeneratorService implements GeneratorService {
    private final PythonService pythonService;
    private final RandomizerService randomizerService;
    private final TriforceBlitzConfig config;
    private final SimpMessagingTemplate messagingTemplate;
    private final ApplicationEventPublisher eventPublisher;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public LocalGeneratorService(PythonService pythonService,
                                 RandomizerService randomizerService,
                                 TriforceBlitzConfig config,
                                 SimpMessagingTemplate messagingTemplate,
                                 ApplicationEventPublisher eventPublisher) {
        this.pythonService = pythonService;
        this.randomizerService = randomizerService;
        this.config = config;
        this.messagingTemplate = messagingTemplate;
        this.eventPublisher = eventPublisher;
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
                config.getSeedStoragePath().resolve(seed.getId()),
                eventPublisher
        ));
        return seed;
    }

    @EventListener
    public void onGeneratorLogEvent(GeneratorLogEvent event) {
        var seed = event.getSeed();
        messagingTemplate.convertAndSend(
                "/topic/seed/" + seed.getId() + "/randomizer/log",
                Map.of(
                        "timestamp", Instant.ofEpochMilli(event.getTimestamp()),
                        "message", event.getMessage()
                )
        );
    }
}
