package com.triforceblitz.triforceblitz.seeds;

import java.util.UUID;

public interface FeaturedSeedService {
    void createDailySeed(UUID seedId);

    void createWeeklySeed(UUID seedId);
}
