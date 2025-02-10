package com.triforceblitz.triforceblitz.randomizer;

import com.triforceblitz.triforceblitz.TriforceBlitzConfig;
import org.springframework.stereotype.Service;

@Service
public class LocalRandomizerService implements RandomizerService {
    private final TriforceBlitzConfig config;

    public LocalRandomizerService(TriforceBlitzConfig config) {
        this.config = config;
    }

    @Override
    public Randomizer getRandomizer(String version) {
        var path = config.getRandomizersPath().resolve(version);
        return new Randomizer(path);
    }
}
