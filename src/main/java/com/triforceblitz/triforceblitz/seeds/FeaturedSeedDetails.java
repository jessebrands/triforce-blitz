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
    public static FeaturedSeedDetails from(Seed seed) {
        var feature = seed.getFeature();
        assert feature != null;
        return new FeaturedSeedDetails(
                seed.getId(),
                feature.getDate(),
                feature.getAddedAt(),
                feature.isDaily(),
                feature.isWeekly(),
                seed.isCooperative(),
                seed.isSpoilerLocked()
        );
    }
}
