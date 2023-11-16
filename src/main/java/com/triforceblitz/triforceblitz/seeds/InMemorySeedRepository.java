package com.triforceblitz.triforceblitz.seeds;

import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class InMemorySeedRepository implements SeedRepository {
    private final Map<UUID, Seed> seeds = new HashMap<>();

    @Override
    public Optional<Seed> findSeedById(UUID id) {
        return Optional.ofNullable(seeds.get(id));
    }

    @Override
    public Seed save(Seed seed) {
        seeds.put(seed.getId(), seed);
        return seed;
    }
}
