package com.triforceblitz.triforceblitz.seeds;

import org.springframework.lang.Nullable;

import java.util.UUID;

public interface FeaturedSeedService {
    @Nullable
    FeaturedSeedDetails getDailySeed();

    @Nullable
    FeaturedSeedDetails getWeeklySeed();

    void createDailySeed(UUID seedId);

    void createWeeklySeed(UUID seedId);
}
