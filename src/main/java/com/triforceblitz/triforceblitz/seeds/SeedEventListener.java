package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.generator.events.GeneratorLogEvent;
import com.triforceblitz.triforceblitz.generator.events.GeneratorStartedEvent;
import com.triforceblitz.triforceblitz.generator.events.GeneratorSuccessEvent;
import com.triforceblitz.triforceblitz.seeds.websocket.GeneratorLogMessage;
import com.triforceblitz.triforceblitz.seeds.websocket.GeneratorStartMessage;
import com.triforceblitz.triforceblitz.seeds.websocket.GeneratorSuccessMessage;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.regex.Pattern;

/**
 * This listener listens for events from the Generator and processes them.
 */
@Component
public class SeedEventListener {
    private final static Pattern FILTER_PATTERN = Pattern.compile(
            "\\bsphere|file|settings|seed|spoiler log|patch? file|version\\b", Pattern.CASE_INSENSITIVE
    );

    private final SimpMessagingTemplate template;
    private final SeedRepository repository;


    private String getDestinationPrefix(Seed seed) {
        return String.format("/topic/seeds/%s", seed.getId());
    }

    public SeedEventListener(SimpMessagingTemplate template, SeedRepository repository) {
        this.template = template;
        this.repository = repository;
    }

    @EventListener
    public void onGeneratorLog(GeneratorLogEvent event) {
        var message = event.getMessage();
        if (!message.isEmpty() && !FILTER_PATTERN.matcher(message).find()) {
            var destination = getDestinationPrefix(event.getSeed()) + "/generator/log";
            var timestamp = event.getTimestamp();
            template.convertAndSend(destination, new GeneratorLogMessage(message, timestamp));
        }
    }

    @EventListener
    public void onGeneratorStart(GeneratorStartedEvent event) {
        var seed = event.getSeed();
        var timestamp = event.getTimestamp();
        var destination = getDestinationPrefix(seed) + "/generator/start";
        template.convertAndSend(destination, new GeneratorStartMessage(timestamp));
    }

    @EventListener
    public void onGeneratorSuccess(GeneratorSuccessEvent event) {
        var seed = repository.save(event.getSeed());

        // Send out the success message.
        var timestamp = event.getTimestamp();
        var destination = getDestinationPrefix(seed) + "/generator/success";
        template.convertAndSend(destination, new GeneratorSuccessMessage(timestamp));
    }
}
