package com.triforceblitz.triforceblitz.seeds.jpa;

import com.triforceblitz.triforceblitz.seeds.Seed;
import com.triforceblitz.triforceblitz.seeds.SeedRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaSeedRepository extends SeedRepository, JpaRepository<Seed, UUID> {
    @Query("""
            select distinct seed from Seed seed
            join fetch seed.racetimeLock lock
            where seed.spoilerLocked = true
            """)
    List<Seed> findAllRacetimeLockedSeeds();
}
