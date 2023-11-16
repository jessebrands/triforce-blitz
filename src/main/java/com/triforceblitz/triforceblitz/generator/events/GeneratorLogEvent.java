package com.triforceblitz.triforceblitz.generator.events;

import org.springframework.context.ApplicationEvent;

public class GeneratorLogEvent extends ApplicationEvent {
    private final String message;

    public GeneratorLogEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
