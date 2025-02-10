package com.triforceblitz.triforceblitz.seeds;

import java.time.Instant;

public class Seed {
    private final String id;
    private Instant createdAt;

    public Seed(String id) {
        this.id = id;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
