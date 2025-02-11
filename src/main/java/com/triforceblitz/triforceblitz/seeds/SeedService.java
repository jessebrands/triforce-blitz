package com.triforceblitz.triforceblitz.seeds;

public interface SeedService {
    SeedDetails getById(String id);
    SeedDetails generateSeed();
}
