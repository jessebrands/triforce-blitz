package com.triforceblitz.triforceblitz.seeds;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record FeaturedSeedDetails(
        UUID id,
        LocalDate date,
        Instant addedAt,
        boolean daily,
        boolean weekly,
        boolean cooperative,
        boolean spoilerLocked
) {
    public static FeaturedSeedDetails from(FeaturedSeed featuredSeed) {
        return new FeaturedSeedDetails(
                featuredSeed.getSeed().getId(),
                featuredSeed.getDate(),
                featuredSeed.getAddedAt(),
                featuredSeed.isDaily(),
                featuredSeed.isWeekly(),
                featuredSeed.getSeed().isCooperative(),
                featuredSeed.getSeed().isSpoilerLocked()
        );
    }
}
