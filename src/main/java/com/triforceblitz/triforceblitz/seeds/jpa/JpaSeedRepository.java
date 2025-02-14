package com.triforceblitz.triforceblitz.seeds.jpa;

import com.triforceblitz.triforceblitz.seeds.Seed;
import com.triforceblitz.triforceblitz.seeds.SeedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaSeedRepository extends SeedRepository, JpaRepository<Seed, UUID> {
    @Query("""
            select distinct seed from Seed seed
            join fetch seed.racetimeLock lock
            where seed.spoilerLocked = true
            """)
    List<Seed> findAllRacetimeLockedSeeds();


    @Query("""
            select distinct seed from Seed seed
            join fetch seed.feature feature
            where feature.daily = true
            order by feature.date desc limit 1
            """)
    Optional<Seed> findLatestDailySeed();

    @Query("""
            select distinct seed from Seed seed
            join fetch seed.feature feature
            where feature.weekly = true
            order by feature.date desc limit 1
            """)
    Optional<Seed> findLatestWeeklySeed();

    @Query("""
            select distinct seed from Seed seed
            join fetch seed.feature feature
            where feature.daily = true
              and feature.weekly = false
              and seed.spoilerLocked = true
              and feature.date != CURRENT_DATE
            order by feature.date desc
            """)
    List<Seed> findAllLockedPreviousDailySeeds();

    @Query("""
            select count(seed) > 0 from Seed seed
            left join seed.feature feature
            where feature.daily = true and feature.date = :date
            """)
    boolean dailySeedExistsByDate(LocalDate date);
}
