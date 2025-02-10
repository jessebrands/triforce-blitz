package com.triforceblitz.triforceblitz.seeds;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemorySeedRepository implements SeedRepository {
    private final Map<String, Seed> seeds = new ConcurrentHashMap<>();

    @Override
    public Optional<Seed> findById(String id) {
        return Optional.ofNullable(seeds.get(id));
    }

    @Override
    public Seed save(Seed seed) {
        seeds.put(seed.getId(), seed);
        return seed;
    }
}
