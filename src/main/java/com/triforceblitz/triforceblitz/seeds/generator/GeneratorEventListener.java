package com.triforceblitz.triforceblitz.seeds.generator;

import com.triforceblitz.triforceblitz.seeds.generator.events.GeneratorFailedEvent;
import com.triforceblitz.triforceblitz.seeds.generator.events.GeneratorFinishedEvent;
import com.triforceblitz.triforceblitz.seeds.generator.events.GeneratorLogEvent;
import com.triforceblitz.triforceblitz.seeds.generator.events.GeneratorStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
public class GeneratorEventListener {
    private final SimpMessagingTemplate messagingTemplate;

    public GeneratorEventListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void onGeneratorLogEvent(GeneratorLogEvent event) {
        var seed = event.getSeed();
        var message = event.getMessage();
        // Filter out unwanted messages!
        if (message.contains(" Seed: ")) {
            return;
        } else if (message.contains("sphere")) {
            return;
        } else if (message.contains("Creating Patch File")) {
            message = "Creating patch file.";
        } else if (message.contains("Creating Patch Archive")) {
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
