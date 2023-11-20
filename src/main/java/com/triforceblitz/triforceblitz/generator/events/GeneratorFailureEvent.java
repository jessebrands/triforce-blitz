package com.triforceblitz.triforceblitz.generator.events;

import com.triforceblitz.triforceblitz.seeds.Seed;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * This application event is sent when a Generator task fails to generate a seed.
 *
 * @author Jesse
 */
@Getter
public class GeneratorFailureEvent extends ApplicationEvent {
    private final Seed seed;

    public GeneratorFailureEvent(Object source, Seed seed) {
        super(source);
        this.seed = seed;
    }
}
