package com.triforceblitz.triforceblitz.generator.events;

import com.triforceblitz.triforceblitz.seeds.Seed;
import org.springframework.context.ApplicationEvent;

/**
 * This event is fired when the Generator has successfully finished generating the seed.
 *
 * @author Jesse
 */
public class GeneratorSuccessEvent extends ApplicationEvent {
    private final Seed seed;

    public GeneratorSuccessEvent(Object source, Seed seed) {
        super(source);
        this.seed = seed;
    }

    public Seed getSeed() {
        return seed;
    }
}
