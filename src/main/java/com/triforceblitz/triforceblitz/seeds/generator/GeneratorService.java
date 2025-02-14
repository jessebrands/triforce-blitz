package com.triforceblitz.triforceblitz.seeds.generator;

import com.triforceblitz.triforceblitz.seeds.SeedNotFoundException;

import java.util.UUID;

public interface GeneratorService {
    void generateSeed(UUID seedId) throws SeedNotFoundException;
}
