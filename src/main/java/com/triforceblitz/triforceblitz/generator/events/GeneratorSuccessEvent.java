package com.triforceblitz.triforceblitz.generator.events;

import com.triforceblitz.triforceblitz.seeds.Seed;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * This event is fired when the Generator has successfully finished generating the seed.
 *
 * @author Jesse
 */
@Getter
public class GeneratorSuccessEvent extends ApplicationEvent {
    private final Seed seed;

    public GeneratorSuccessEvent(Object source, Seed seed) {
        super(source);
        this.seed = seed;
    }
}
