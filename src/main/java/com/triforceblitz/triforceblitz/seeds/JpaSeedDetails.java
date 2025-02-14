package com.triforceblitz.triforceblitz.seeds;

import java.time.Instant;
import java.util.UUID;

public class JpaSeedDetails implements SeedDetails {
    private final Seed seed;

    public JpaSeedDetails(Seed seed) {
        this.seed = seed;
    }

    @Override
    public UUID getId() {
        return seed.getId();
    }

    @Override
    public String getSeed() {
        return seed.getSeed();
    }

    @Override
    public String getPreset() {
        return seed.getPreset();
    }

    @Override
    public String getVersion() {
        return seed.getRandomizerVersion();
    }

    @Override
    public boolean isSpoilerLocked() {
        return seed.isSpoilerLocked();
    }

    @Override
    public boolean isCooperative() {
        return seed.isCooperative();
    }

    @Override
    public Instant getCreatedAt() {
        return seed.getCreatedAt();
    }
}
