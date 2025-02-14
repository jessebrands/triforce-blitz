package com.triforceblitz.triforceblitz.seeds.spoilerlog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triforceblitz.triforceblitz.TriforceBlitzConfig;
import com.triforceblitz.triforceblitz.seeds.Seed;
import com.triforceblitz.triforceblitz.seeds.SeedRepository;
import com.triforceblitz.triforceblitz.seeds.errors.SeedNotFoundException;
import com.triforceblitz.triforceblitz.seeds.errors.SeedNotGeneratedException;
import com.triforceblitz.triforceblitz.seeds.generator.GeneratedSeedService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.UUID;

@Service
public class JpaSpoilerLogManager implements SpoilerLogManager {
    private final SeedRepository seedRepository;
    private final GeneratedSeedService generatedSeedService;
    private final TriforceBlitzConfig config;

    public JpaSpoilerLogManager(SeedRepository seedRepository,
                                GeneratedSeedService generatedSeedService,
                                TriforceBlitzConfig config) {
        this.seedRepository = seedRepository;
        this.generatedSeedService = generatedSeedService;
        this.config = config;
    }

    @Override
    public void lockSpoilerLog(UUID seedId) {
        seedRepository.findById(seedId).ifPresent(seed -> {
            seed.setSpoilerLocked(true);
            seedRepository.save(seed);
        });
    }

    @Override
    public void unlockSpoilerLog(UUID seedId) {
        seedRepository.findById(seedId).ifPresent(seed -> {
            seed.setSpoilerLocked(false);
            seedRepository.save(seed);
        });
    }

    @Override
    public SpoilerLog loadSpoilerLogBySeedId(UUID seedId) throws SeedNotGeneratedException {
        try {
            var generatedSeed = generatedSeedService.loadGeneratedSeedBySeedId(seedId);
            var spoilerLogFile = generatedSeed.spoilerLogFilename().toFile();
            return new ObjectMapper().readValue(spoilerLogFile, SpoilerLog.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public boolean isSpoilerLogLocked(UUID seedId) throws SeedNotFoundException {
        return seedRepository.findById(seedId)
                .map(Seed::isSpoilerLocked)
                .orElseThrow(() -> new SeedNotFoundException("not found"));
    }
}
