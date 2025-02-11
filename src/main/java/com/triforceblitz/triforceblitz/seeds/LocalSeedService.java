package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.TriforceBlitzConfig;
import com.triforceblitz.triforceblitz.seeds.generator.GeneratorService;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
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

    private Path getSeedLocation(UUID id) {
        return config.getSeedStoragePath().resolve(id.toString());
    }

    @Override
    public SeedDetails getById(UUID id) {
        return seedRepository.findById(id)
                .map(seed -> new SeedDetails(
                        seed.getId().toString(),
                        getSeedLocation(seed.getId())
                ))
                .orElseThrow(() -> new RuntimeException("seed not found"));
    }

    @Override
    public SeedDetails generateSeed() {
        var seed = generatorService.generateSeed(
                config.getRandomizerVersion(),
                UUID.randomUUID().toString(),
                config.getRandomizerPreset()
        );
        seedRepository.save(seed);
        return getById(seed.getId());
    }

    @Override
    public Path getPatchFilename(UUID id) {
        var seed = getById(id);
        return config.getSeedStoragePath()
                .resolve(seed.getId())
                .resolve("TriforceBlitz.zpf");
    }

    @Override
    public Path getSpoilerLogFilename(UUID id) {
        var seed = getById(id);
        return config.getSeedStoragePath()
                .resolve(seed.getId())
                .resolve("TriforceBlitz_Spoiler.json");
    }
}
