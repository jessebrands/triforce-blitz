package com.triforceblitz.triforceblitz.seeds;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeedRepository {
    Optional<Seed> findById(UUID id);

    List<Seed> findAllRacetimeLockedSeeds();

    Optional<Seed> findLatestDailySeed();

    Optional<Seed> findLatestWeeklySeed();

    List<Seed> findAllLockedPreviousDailySeeds();

    List<Seed> findAllLockedPreviousWeeklySeeds();

    Seed save(Seed seed);

    boolean existsById(UUID id);

    boolean dailySeedExistsByDate(LocalDate date);

    void deleteById(UUID id);
}
