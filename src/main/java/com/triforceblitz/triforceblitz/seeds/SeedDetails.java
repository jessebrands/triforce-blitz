package com.triforceblitz.triforceblitz.seeds;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.UUID;

public record SeedDetails(
            UUID id,
            String seed,
            String preset,
            String version,
            boolean cooperative,
            boolean spoilerLocked,
            Instant createdAt,
            Path location,
            String racetimeSlug
    ) {
    public Path getPatchFile() {
        if (!cooperative) {
            return location.resolve(Seed.PATCH_FILENAME);
        } else {
            return location.resolve(Seed.COOP_PATCH_FILENAME);
        }
    }

    public Path getSpoilerLogFile() {
        return location.resolve(Seed.SPOILER_LOG_FILENAME);
    }

    public boolean isGenerated() {
        return Files.exists(getPatchFile());
    }

    public Instant getGeneratedAt() throws IOException {
        var attrs = Files.readAttributes(getPatchFile(), BasicFileAttributes.class);
        var creationTime = attrs.creationTime();
        return creationTime.toInstant();
    }

    public String getRacetimeSlug() {
        return racetimeSlug;
    }
}
