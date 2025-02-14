package com.triforceblitz.triforceblitz.seeds.racetime;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RacetimeLockRepository extends CrudRepository<RacetimeLock, UUID> {
    @Query("""
            select l
            from RacetimeLock l
            join fetch l.seed s
            where s.id = :id
            """)
    Optional<RacetimeLock> findBySeedId(UUID id);

    @Query("""
            select l
            from RacetimeLock l
            join fetch l.seed
            where l.seed.spoilerLocked = true
            """)
    List<RacetimeLock> findAllLockedLocks();
}
