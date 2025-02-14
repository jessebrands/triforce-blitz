package com.triforceblitz.triforceblitz.seeds.spoilerlog;

import com.triforceblitz.triforceblitz.seeds.errors.SeedNotGeneratedException;

import java.util.UUID;

public interface SpoilerLogService {
    SpoilerLog loadSpoilerLogBySeedId(UUID seedId) throws SeedNotGeneratedException;
}
