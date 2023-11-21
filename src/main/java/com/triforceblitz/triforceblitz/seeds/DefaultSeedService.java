package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.TriforceBlitzVersion;
import com.triforceblitz.triforceblitz.generator.GeneratorService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultSeedService implements SeedService {
    private final SeedRepository seedRepository;
    private final GeneratorService generatorService;

    public DefaultSeedService(SeedRepository seedRepository, GeneratorService generatorService) {
        this.seedRepository = seedRepository;
        this.generatorService = generatorService;
    }

    @Override
    public Seed getSeed(UUID id) {
        return seedRepository.findById(id).orElseThrow();
    }

    @Override
    public Seed generateSeed(TriforceBlitzVersion version) throws Exception {
        var randomizerSeed = UUID.randomUUID().toString();
        var seed = generatorService.generateSeed(version, randomizerSeed);
        return seedRepository.insert(seed);
    }

    @Override
    public void lockSpoilerLog(Seed seed) {
        seed.setSpoilerLocked(true);
        seedRepository.update(seed);
    }

    @Override
    public void unlockSpoilerLog(Seed seed) {
        seed.setSpoilerLocked(false);
        seedRepository.update(seed);
    }
}
