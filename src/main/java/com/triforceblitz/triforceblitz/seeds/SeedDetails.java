package com.triforceblitz.triforceblitz.seeds;

import java.time.Instant;
import java.util.UUID;

public interface SeedDetails {
    UUID getId();

    String getSeed();

    String getPreset();

    String getVersion();

    boolean isSpoilerLocked();

    boolean isCooperative();

    Instant getCreatedAt();
}
