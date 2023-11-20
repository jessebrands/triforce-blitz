package com.triforceblitz.triforceblitz.generator.events;

import com.triforceblitz.triforceblitz.seeds.Seed;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Describes an event sent from a Generator task indicating its task has begun.
 *
 * @author Jesse
 */
@Getter
public class GeneratorStartedEvent extends ApplicationEvent {
    private final Seed seed;

    public GeneratorStartedEvent(Object source, Seed seed) {
        super(source);
        this.seed = seed;
    }
}
