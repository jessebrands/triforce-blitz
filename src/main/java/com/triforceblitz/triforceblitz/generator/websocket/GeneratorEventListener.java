package com.triforceblitz.triforceblitz.generator.websocket;

import com.triforceblitz.triforceblitz.generator.events.GeneratorStatusEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class GeneratorEventListener {
    private final static Logger logger = LoggerFactory.getLogger(GeneratorEventListener.class);
    private final static Pattern FILTER_PATTERN = Pattern.compile(
            "\\bsphere|file|settings|seed|spoiler log|patch? file|version\\b", Pattern.CASE_INSENSITIVE
    );

    private final SimpMessagingTemplate template;

    private MessageHeaders createHeaders(String sessionId) {
        var headers = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headers.setSessionId(sessionId);
        headers.setLeaveMutable(true);
        return headers.getMessageHeaders();
    }

    public GeneratorEventListener(SimpMessagingTemplate template) {
        this.template = template;
    }

    @EventListener
    public void onStatusEvent(GeneratorStatusEvent event) {
        var sessionId = event.getSessionId();
        var matcher = FILTER_PATTERN.matcher(event.getMessage());

        if (sessionId != null && !matcher.find()) {
            var payload = new GeneratorStatusMessage(event.getMessage());
            template.convertAndSendToUser(sessionId, "/topic/generator/status", payload, createHeaders(sessionId));
        }
        logger.info(event.getMessage());
    }
}
