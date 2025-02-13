package com.triforceblitz.triforceblitz.seeds;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.UUID;

public class SeedDetails {
    private final UUID id;
    private final String seed;
    private final String preset;
    private final String version;
    private final boolean cooperative;
    private final boolean spoilerLocked;
    private final Instant createdAt;
    private final Path location;
    private final String racetimeSlug;

    public SeedDetails(
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
        this.id = id;
        this.seed = seed;
        this.preset = preset;
        this.version = version;
        this.cooperative = cooperative;
        this.spoilerLocked = spoilerLocked;
        this.createdAt = createdAt;
        this.location = location;
        this.racetimeSlug = racetimeSlug;
    }

    public UUID getId() {
        return id;
    }

    public String getSeed() {
        return seed;
    }

    public String getPreset() {
        return preset;
    }

    public String getVersion() {
        return version;
    }

    public boolean isCooperative() {
        return cooperative;
    }

    public boolean isSpoilerLocked() {
        return spoilerLocked;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Path getLocation() {
        return location;
    }

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
