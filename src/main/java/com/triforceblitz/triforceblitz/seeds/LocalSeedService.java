package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.TriforceBlitzConfig;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LocalSeedService implements SeedService {
    private final TriforceBlitzConfig config;
    private final GeneratorService generatorService;

    public LocalSeedService(TriforceBlitzConfig config,
                            GeneratorService generatorService) {
        this.config = config;
        this.generatorService = generatorService;
    }

    @Override
    public Seed generateSeed() {
        return generatorService.generateSeed(
                config.getRandomizerVersion(),
                UUID.randomUUID().toString(),
                config.getRandomizerPreset()
        );
    }
}
