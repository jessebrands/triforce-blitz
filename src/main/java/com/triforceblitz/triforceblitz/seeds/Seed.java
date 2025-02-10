package com.triforceblitz.triforceblitz.seeds;

import java.time.Instant;

public class Seed {
    private final String id;
    private String seed;
    private Instant createdAt;
    private Instant generatedAt;

    public Seed(String id, String seed) {
        this.id = id;
        this.seed = seed;
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
