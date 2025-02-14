package com.triforceblitz.triforceblitz.seeds.generator;

import com.triforceblitz.triforceblitz.TriforceBlitzConfig;
import com.triforceblitz.triforceblitz.seeds.SeedNotGeneratedException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;

@Service
public class FilesystemGeneratedSeedService implements GeneratedSeedService {
    private final TriforceBlitzConfig config;

    public FilesystemGeneratedSeedService(TriforceBlitzConfig config) {
        this.config = config;
    }

    private Path getSeedDirectory(UUID seedId) throws SeedNotGeneratedException {
        var seedStoragePath = config.getSeedStoragePath();
        var seedDirectory = seedStoragePath.resolve(seedId.toString());
        if (!Files.isDirectory(seedDirectory)) {
            throw new SeedNotGeneratedException("seed directory does not exist");
        }
        return seedDirectory;
    }

    private Path getPatchFilename(UUID seedId) throws SeedNotGeneratedException {
        var seedDirectory = getSeedDirectory(seedId);
        var patchFilename = seedDirectory.resolve("TriforceBlitz.zpf");
        var multiWorldPatchFilename = seedDirectory.resolve("TriforceBlitz.zpfz");
        if (Files.exists(patchFilename)) {
            return patchFilename;
        } else if (Files.exists(multiWorldPatchFilename)) {
            return multiWorldPatchFilename;
        } else {
            throw new SeedNotGeneratedException("patch file or archive does not exist");
        }
    }

    private Path getSpoilerLogFilename(UUID seedId) throws SeedNotGeneratedException {
        var seedDirectory = getSeedDirectory(seedId);
        var spoilerFilename = seedDirectory.resolve("TriforceBlitz_Spoiler.json");
        if (!Files.isRegularFile(spoilerFilename)) {
            throw new SeedNotGeneratedException("spoiler log file does not exist");
        }
        return spoilerFilename;
    }

    @Override
    public GeneratedSeed loadGeneratedSeedBySeedId(UUID seedId) throws SeedNotGeneratedException {
        try {
            var patchFilename = getPatchFilename(seedId);
            var attributes = Files.readAttributes(patchFilename, BasicFileAttributes.class);
            return new GeneratedSeed(
                    getSpoilerLogFilename(seedId),
                    patchFilename,
                    attributes.creationTime().toInstant()
            );
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
