package com.triforceblitz.triforceblitz.seeds.generator.events;

import com.triforceblitz.triforceblitz.seeds.Seed;
import org.springframework.context.ApplicationEvent;

public class GeneratorFinishedEvent extends ApplicationEvent {
    public GeneratorFinishedEvent(Seed source) {
        super(source);
    }

    public Seed getSeed() {
        return (Seed) this.getSource();
    }
}
