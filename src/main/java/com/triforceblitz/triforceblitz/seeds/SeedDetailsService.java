package com.triforceblitz.triforceblitz.seeds;

import java.util.UUID;

public interface SeedDetailsService {
    SeedDetails loadSeedById(UUID id) throws SeedNotFoundException;
}
