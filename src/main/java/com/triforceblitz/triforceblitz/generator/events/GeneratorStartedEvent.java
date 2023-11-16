package com.triforceblitz.triforceblitz.generator.events;

import com.triforceblitz.triforceblitz.seeds.Seed;
import org.springframework.context.ApplicationEvent;

/**
 * Describes an event sent from a Generator task indicating its task has begun.
 *
 * @author Jesse
 */
public class GeneratorStartedEvent extends ApplicationEvent {
    private final Seed seed;

    public GeneratorStartedEvent(Object source, Seed seed) {
        super(source);
        this.seed = seed;
    }

    public Seed getSeed() {
        return seed;
    }
}
