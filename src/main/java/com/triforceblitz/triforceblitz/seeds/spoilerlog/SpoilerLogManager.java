package com.triforceblitz.triforceblitz.seeds.spoilerlog;

import com.triforceblitz.triforceblitz.seeds.errors.SeedNotFoundException;

import java.util.UUID;

public interface SpoilerLogManager extends SpoilerLogService, SpoilerLogFileService {
    void lockSpoilerLog(UUID seedId);

    void unlockSpoilerLog(UUID seedId);

    boolean isSpoilerLogLocked(UUID seedId) throws SeedNotFoundException;
}
