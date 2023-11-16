package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.generator.events.GeneratorLogEvent;
import com.triforceblitz.triforceblitz.seeds.websocket.GeneratorLogMessage;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

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

    public SeedEventListener(SimpMessagingTemplate template) {
        this.template = template;
    }

    @EventListener
    public void onGeneratorLog(GeneratorLogEvent event) {
        var message = event.getMessage();
        if (!message.isEmpty() && !FILTER_PATTERN.matcher(message).find()) {
            var destination = String.format("/topic/seeds/%s/log", event.getSeed().getId());
            var timestamp = event.getTimestamp();
            template.convertAndSend(destination, new GeneratorLogMessage(message, timestamp));
        }
    }
}
