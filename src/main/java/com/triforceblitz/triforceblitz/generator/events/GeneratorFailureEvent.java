package com.triforceblitz.triforceblitz.generator.events;

import com.triforceblitz.triforceblitz.seeds.Seed;
import org.springframework.context.ApplicationEvent;

/**
 * This application event is sent when a Generator task fails to generate a seed.
 *
 * @author Jesse
 */
public class GeneratorFailureEvent extends ApplicationEvent {
    private final Seed seed;

    public GeneratorFailureEvent(Object source, Seed seed) {
        super(source);
        this.seed = seed;
    }

    public Seed getSeed() {
        return seed;
    }
}
