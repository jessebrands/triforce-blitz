package com.triforceblitz.triforceblitz.seeds;

public interface SeedService {
    Seed getById(String id);
    Seed generateSeed();
}
