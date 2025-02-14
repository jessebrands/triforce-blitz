package com.triforceblitz.triforceblitz.seeds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SeedRepository extends JpaRepository<Seed, UUID> {
    @Query("""
            select distinct seed from Seed seed
            join fetch seed.racetimeLock lock
            where seed.spoilerLocked = true
            """)
    List<Seed> findAllRacetimeLockedSeeds();
}
