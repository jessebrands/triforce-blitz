package com.triforceblitz.triforceblitz.seeds;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeedRepository {
    Optional<Seed> findById(UUID id);

    List<Seed> findAllRacetimeLockedSeeds();

    Seed save(Seed seed);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
