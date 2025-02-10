package com.triforceblitz.triforceblitz.seeds;

import java.util.Optional;

public interface SeedRepository {
    Optional<Seed> findById(String id);

    Seed save(Seed seed);
}
