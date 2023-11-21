package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.TriforceBlitzVersion;

import java.util.UUID;

public interface SeedService {
    Seed getSeed(UUID id);

    Seed generateSeed(TriforceBlitzVersion version) throws Exception;

    void lockSpoilerLog(Seed seed);

    void unlockSpoilerLog(Seed seed);
}
