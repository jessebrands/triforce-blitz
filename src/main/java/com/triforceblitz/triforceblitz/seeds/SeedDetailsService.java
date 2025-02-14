package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.seeds.errors.SeedNotFoundException;

import java.util.UUID;

public interface SeedDetailsService {
    SeedDetails loadSeedById(UUID id) throws SeedNotFoundException;
}
