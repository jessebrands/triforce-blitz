package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.generator.GeneratorConfig;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class LocalSeedService implements SeedService {
    private final GeneratorConfig config;

    public LocalSeedService(GeneratorConfig config) {
        this.config = config;
    }

    @Override
    public Optional<Path> getPatchFilename(Seed seed) {
        var seedId = seed.getId().toString();
        var seedPath = config.getSeedsPath().resolve(seedId);
        var patchFilename = seedPath.resolve(seedId + ".zpf");
        if (Files.exists(patchFilename) && Files.isRegularFile(patchFilename)) {
            return Optional.of(patchFilename);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Path> getSpoilerFilename(Seed seed) {
        var seedId = seed.getId().toString();
        var seedPath = config.getSeedsPath().resolve(seedId);
        var spoilerFilename = seedPath.resolve(seedId + "_Spoiler.json");
        if (Files.exists(spoilerFilename) && Files.isRegularFile(spoilerFilename)) {
            return Optional.of(spoilerFilename);
        }
        return Optional.empty();
    }
}
