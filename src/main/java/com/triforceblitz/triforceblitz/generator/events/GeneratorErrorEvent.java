package com.triforceblitz.triforceblitz.generator.events;

import com.triforceblitz.triforceblitz.seeds.Seed;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * This application event is sent when the generator task encounters an exception.
 *
 * @author Jesse
 */
public class GeneratorErrorEvent extends ApplicationEvent {
    private final Seed seed;
    private final Exception exception;

    public GeneratorErrorEvent(Object source, Seed seed, Exception exception) {
        super(source);
        this.seed = seed;
        this.exception = exception;
    }

    public Seed getSeed() {
        return seed;
    }

    public Exception getException() {
        return exception;
    }
}
