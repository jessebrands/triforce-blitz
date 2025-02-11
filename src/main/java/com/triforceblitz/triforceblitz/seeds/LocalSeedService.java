package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.TriforceBlitzConfig;
import com.triforceblitz.triforceblitz.seeds.generator.GeneratorService;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Optional;
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

    private SeedDetails seedToDetails(Seed seed) {
        return new SeedDetails(
                seed.getId(),
                seed.getSeed(),
                seed.getPreset(),
                seed.getRandomizerVersion(),
                seed.isCooperative(),
                seed.isSpoilerLocked(),
                seed.getCreatedAt(),
                getSeedLocation(seed.getId())
        );
    }

    private Path getSeedLocation(UUID id) {
        return config.getSeedStoragePath().resolve(id.toString());
    }

    @Override
    public Optional<SeedDetails> getById(UUID id) {
        return seedRepository.findById(id)
                .map(this::seedToDetails);
    }

    @Override
    public SeedDetails generateSeed() {
        var seed = generatorService.generateSeed(
                config.getRandomizerVersion(),
                UUID.randomUUID().toString(),
                config.getRandomizerPreset()
        );
        seedRepository.save(seed);
        return seedToDetails(seed);
    }

    @Override
    public void lockSpoilerLog(UUID id) {
        seedRepository.findById(id).ifPresent(seed -> {
            seed.lockSpoiler();
            seedRepository.save(seed);
        });
    }

    @Override
    public void unlockSpoilerLog(UUID id) {
        seedRepository.findById(id).ifPresent(seed -> {
            seed.unlockSpoiler();
            seedRepository.save(seed);
        });
    }

    @Override
    public Path getPatchFilename(UUID id) {
        return getById(id)
                .map(SeedDetails::getPatchFile)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public Path getSpoilerLogFilename(UUID id) {
        return getById(id)
                .map(SeedDetails::getSpoilerLogFile)
                .orElseThrow(() -> new RuntimeException("not found"));
    }
}
