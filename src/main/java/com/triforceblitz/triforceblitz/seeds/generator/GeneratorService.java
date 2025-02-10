package com.triforceblitz.triforceblitz.seeds.generator;

import com.triforceblitz.triforceblitz.seeds.Seed;

public interface GeneratorService {
    Seed generateSeed(String version, String seed, String preset);
}
