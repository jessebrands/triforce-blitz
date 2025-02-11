package com.triforceblitz.triforceblitz.seeds;

import java.time.Instant;
import java.util.UUID;

public class Seed {
    public static final String PATCH_FILENAME = "TriforceBlitz.zpf";
    public static final String COOP_PATCH_FILENAME = "TriforceBlitz.zpfz";
    public static final String SPOILER_LOG_FILENAME = "TriforceBlitz_Spoiler.json";

    private final UUID id;
    private final String seed;
    private final String preset;
    private final String version;
    private final boolean cooperative;
    private boolean spoilerLocked = false;
    private final Instant createdAt;

    public Seed(String seed, String preset, String version, boolean cooperative) {
        this.version = version;
        this.cooperative = cooperative;
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

    void lockSpoiler() {
        spoilerLocked = true;
    }

     void unlockSpoiler() {
        spoilerLocked = false;
     }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
