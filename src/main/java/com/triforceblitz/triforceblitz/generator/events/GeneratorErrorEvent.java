package com.triforceblitz.triforceblitz.generator.events;

import com.triforceblitz.triforceblitz.seeds.Seed;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * This application event is sent when the generator task encounters an exception.
 *
 * @author Jesse
 */
@Getter
public class GeneratorErrorEvent extends ApplicationEvent {
    private final Seed seed;
    private final Exception exception;

    public GeneratorErrorEvent(Object source, Seed seed, Exception exception) {
        super(source);
        this.seed = seed;
        this.exception = exception;
    }
}
