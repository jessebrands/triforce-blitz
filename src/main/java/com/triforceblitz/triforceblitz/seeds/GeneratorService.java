package com.triforceblitz.triforceblitz.seeds;

public interface GeneratorService {
    Seed generateSeed(String version, String seed, String preset);
}
