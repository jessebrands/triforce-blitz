package com.triforceblitz.triforceblitz.generator.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.Instant;
import java.util.UUID;

public class GeneratorStatusEvent extends ApplicationEvent {
    private final UUID seedUuid;
    private final String message;

    @Nullable
    private final String sessionId;

    public GeneratorStatusEvent(Object source, UUID seedUuid, String message) {
        super(source);
        this.seedUuid = seedUuid;
        this.message = message;
        this.sessionId = null;
    }

    public GeneratorStatusEvent(Object source, UUID seedUuid, String message,
                                @NonNull String sessionId) {
        super(source);
        this.seedUuid = seedUuid;
        this.message = message;
        this.sessionId = sessionId;
    }

    public UUID getSeedUuid() {
        return seedUuid;
    }

    public String getMessage() {
        return message;
    }

    @Nullable
    public String getSessionId() {
        return sessionId;
    }
}
