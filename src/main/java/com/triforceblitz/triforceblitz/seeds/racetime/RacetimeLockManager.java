package com.triforceblitz.triforceblitz.seeds.racetime;

import com.triforceblitz.triforceblitz.racetime.errors.RaceNotFoundException;
import com.triforceblitz.triforceblitz.seeds.errors.SeedNotFoundException;

import java.util.UUID;

public interface RacetimeLockManager extends RacetimeLockDetailsService {
    void lockSpoilerLogWithRace(UUID id, String category, String raceSlug)
            throws RaceNotFoundException, InvalidRaceException, SeedNotFoundException;
}
