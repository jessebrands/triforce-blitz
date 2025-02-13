package com.triforceblitz.triforceblitz.seeds.generator;

import com.triforceblitz.triforceblitz.TriforceBlitzConfig;
import com.triforceblitz.triforceblitz.python.PythonService;
import com.triforceblitz.triforceblitz.randomizer.RandomizerService;
import com.triforceblitz.triforceblitz.seeds.Seed;
import com.triforceblitz.triforceblitz.seeds.generator.events.GeneratorFailedEvent;
import com.triforceblitz.triforceblitz.seeds.generator.events.GeneratorFinishedEvent;
import com.triforceblitz.triforceblitz.seeds.generator.events.GeneratorLogEvent;
import com.triforceblitz.triforceblitz.seeds.generator.events.GeneratorStartedEvent;
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
    public void generateSeed(Seed seed) {
        var interpreter = pythonService.findInterpreter();
        var randomizer = randomizerService.getRandomizer(seed.getRandomizerVersion());
        executor.submit(new GenerateSeedTask(
                interpreter,
                randomizer,
                seed,
                config.getRomFile(),
                config.getSeedStoragePath().resolve(seed.getId().toString()),
                eventPublisher
        ));
    }

    @EventListener
    public void onGeneratorLogEvent(GeneratorLogEvent event) {
        var seed = event.getSeed();
        var message = event.getMessage();
        // Filter out unwanted messages!
        if (message.contains(" Seed: ")) {
            return;
        }
        else if (message.contains("sphere")) {
            return;
        }
        else if (message.contains("Creating Patch File")) {
            message = "Creating patch file.";
        }
        else if (message.contains("Creating Patch Archive")) {
            message = "Creating multi-world patch archive.";
        } else if (message.contains("Created patch file archive")) {
            return;
        } else if (message.contains("Created spoiler log")) {
            return;
        }
        messagingTemplate.convertAndSend(
                "/topic/seed/" + seed.getId() + "/generator/log",
                Map.of(
                        "timestamp", Instant.ofEpochMilli(event.getTimestamp()),
                        "message", message
                )
        );
    }

    @EventListener
    public void onGeneratorStartedEvent(GeneratorStartedEvent event) {
        var seed = event.getSeed();
        messagingTemplate.convertAndSend(
                "/topic/seed/" + seed.getId() + "/generator/status",
                Map.of(
                        "timestamp", Instant.ofEpochMilli(event.getTimestamp()),
                        "status", GenerateSeedStatus.STARTED
                )
        );
    }

    @EventListener
    public void onGeneratorFinishedEvent(GeneratorFinishedEvent event) {
        var seed = event.getSeed();
        messagingTemplate.convertAndSend(
                "/topic/seed/" + seed.getId() + "/generator/status",
                Map.of(
                        "timestamp", Instant.ofEpochMilli(event.getTimestamp()),
                        "status", GenerateSeedStatus.GENERATED
                )
        );
    }

    @EventListener
    public void onGeneratorFailedEvent(GeneratorFailedEvent event) {
        var seed = event.getSeed();
        messagingTemplate.convertAndSend(
                "/topic/seed/" + seed.getId() + "/generator/status",
                Map.of(
                        "timestamp", Instant.ofEpochMilli(event.getTimestamp()),
                        "status", GenerateSeedStatus.FAILED
                )
        );
    }
}
