package com.triforceblitz.triforceblitz.seeds;

import java.time.Instant;
import java.util.UUID;

public class Seed {
    private final String id;
    private String seed;
    private String preset;
    private Instant createdAt;
    private Instant generatedAt;

    public Seed(String seed, String preset) {
        this.id = UUID.randomUUID().toString();
        this.seed = seed;
        this.preset = preset;
        this.createdAt = Instant.now();
    }

    public String getId() {
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

    public Instant getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Instant generatedAt) {
        this.generatedAt = generatedAt;
    }
}
