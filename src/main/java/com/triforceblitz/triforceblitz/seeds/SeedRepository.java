package com.triforceblitz.triforceblitz.seeds;

import java.util.Optional;
import java.util.UUID;

public interface SeedRepository {
    Optional<Seed> findById(UUID id);

    Seed save(Seed seed);
}
