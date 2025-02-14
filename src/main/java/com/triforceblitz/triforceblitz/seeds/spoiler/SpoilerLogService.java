package com.triforceblitz.triforceblitz.seeds.spoiler;

import com.triforceblitz.triforceblitz.seeds.SeedNotGeneratedException;

import java.util.UUID;

public interface SpoilerLogService {
    SpoilerLog loadSpoilerLogBySeedId(UUID seedId) throws SeedNotGeneratedException;
}
