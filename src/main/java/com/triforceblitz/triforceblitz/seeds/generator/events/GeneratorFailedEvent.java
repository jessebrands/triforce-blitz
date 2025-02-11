package com.triforceblitz.triforceblitz.seeds.generator.events;

import com.triforceblitz.triforceblitz.seeds.Seed;
import org.springframework.context.ApplicationEvent;

public class GeneratorFailedEvent extends ApplicationEvent {
    private final Throwable cause;

    public GeneratorFailedEvent(Seed source, Throwable cause) {
        super(source);
        this.cause = cause;
    }

    public Seed getSeed() {
        return (Seed) this.getSource();
    }

    public Throwable getCause() {
        return cause;
    }
}
