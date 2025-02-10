package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.TriforceBlitzConfig;
import com.triforceblitz.triforceblitz.seeds.generator.GeneratorService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LocalSeedService implements SeedService {
    private final TriforceBlitzConfig config;
    private final GeneratorService generatorService;
    private final SeedRepository seedRepository;

    public LocalSeedService(TriforceBlitzConfig config,
                            GeneratorService generatorService,
                            SeedRepository seedRepository) {
        this.config = config;
        this.generatorService = generatorService;
        this.seedRepository = seedRepository;
    }

    @Override
    public Seed getById(String id) {
        return seedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("seed not found"));
    }

    @Override
    public Seed generateSeed() {
        var seed = generatorService.generateSeed(
                config.getRandomizerVersion(),
                UUID.randomUUID().toString(),
                config.getRandomizerPreset()
        );
        return seedRepository.save(seed);
    }
}
