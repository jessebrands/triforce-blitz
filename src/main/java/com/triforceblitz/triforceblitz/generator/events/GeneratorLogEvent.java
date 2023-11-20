package com.triforceblitz.triforceblitz.generator.events;

import com.triforceblitz.triforceblitz.seeds.Seed;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class GeneratorLogEvent extends ApplicationEvent {
    private final Seed seed;
    private final String message;

    public GeneratorLogEvent(Object source, Seed seed, String message) {
        super(source);
        this.seed = seed;
        this.message = message;
    }
}
