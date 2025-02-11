package com.triforceblitz.triforceblitz.seeds;

import java.time.Instant;
import java.util.UUID;

public class Seed {
    private final UUID id;
    private String seed;
    private String preset;
    private Instant createdAt;

    public Seed(String seed, String preset) {
        this.id = UUID.randomUUID();
        this.seed = seed;
        this.preset = preset;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
