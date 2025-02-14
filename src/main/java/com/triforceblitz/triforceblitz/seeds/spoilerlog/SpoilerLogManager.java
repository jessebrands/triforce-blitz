package com.triforceblitz.triforceblitz.seeds.spoilerlog;

import com.triforceblitz.triforceblitz.seeds.errors.SeedNotFoundException;

import java.util.UUID;

public interface SpoilerLogManager extends SpoilerLogService {
    void lockSpoilerLog(UUID seedId);

    void unlockSpoilerLog(UUID seedId);

    boolean isSpoilerLogLocked(UUID seedId) throws SeedNotFoundException;
}
