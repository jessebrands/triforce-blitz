package com.triforceblitz.triforceblitz.seeds;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeaturedSeedRepository
        extends PagingAndSortingRepository<FeaturedSeed, UUID>, CrudRepository<FeaturedSeed, UUID> {
    boolean existsByDateAndDaily(LocalDate date, boolean daily);

    @Query("""
            select fs
            from FeaturedSeed fs
            join fetch fs.seed s
            where fs.daily = true
            order by fs.date desc limit 1
            """)
    Optional<FeaturedSeed> findLatestDailySeed();

    @Query("""
            select fs
            from FeaturedSeed fs
            join fetch fs.seed s
            where fs.weekly = true
            order by fs.date desc limit 1
            """)
    Optional<FeaturedSeed> findLatestWeeklySeed();

    @Query("""
            select fs
            from FeaturedSeed fs
            join fetch fs.seed s
            where fs.daily = true and not fs.date = CURRENT_DATE and s.spoilerLocked = true
            """)
    List<FeaturedSeed> findAllLockedPreviousDailySeeds();
}
