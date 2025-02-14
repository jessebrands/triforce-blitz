package com.triforceblitz.triforceblitz.seeds;

import java.util.UUID;

public interface SeedDetailsManager extends SeedDetailsService {
    SeedDetails createSeed(boolean cooperative);

    void deleteSeed(UUID id);

    boolean seedExists(UUID id);
}
