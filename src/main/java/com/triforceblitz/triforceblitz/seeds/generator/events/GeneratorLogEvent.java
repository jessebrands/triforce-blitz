package com.triforceblitz.triforceblitz.seeds.generator.events;

import com.triforceblitz.triforceblitz.seeds.Seed;
import org.springframework.context.ApplicationEvent;

public class GeneratorLogEvent extends ApplicationEvent {
    private final String message;

    public GeneratorLogEvent(Seed source, String message) {
        super(source);
        this.message = message;
    }

    public Seed getSeed() {
        return (Seed) this.getSource();
    }

    public String getMessage() {
        return message;
    }
}
