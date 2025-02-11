package com.triforceblitz.triforceblitz.seeds;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemorySeedRepository implements SeedRepository {
    private final Map<UUID, Seed> seeds = new ConcurrentHashMap<>();

    @Override
    public Optional<Seed> findById(UUID id) {
        return Optional.ofNullable(seeds.get(id));
    }

    @Override
    public Seed save(Seed seed) {
        seeds.put(seed.getId(), seed);
        return seed;
    }
}
