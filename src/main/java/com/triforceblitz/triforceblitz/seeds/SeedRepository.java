package com.triforceblitz.triforceblitz.seeds;

import java.util.Optional;
import java.util.UUID;

public interface SeedRepository {
    Optional<Seed> findSeedById(UUID id);
    Seed save(Seed seed);
}
