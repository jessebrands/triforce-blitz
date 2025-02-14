package com.triforceblitz.triforceblitz.seeds.generator;

import com.triforceblitz.triforceblitz.seeds.SeedNotGeneratedException;

import java.util.UUID;

public interface GeneratedSeedService {
    GeneratedSeed loadGeneratedSeedBySeedId(UUID seedId) throws SeedNotGeneratedException;
}
