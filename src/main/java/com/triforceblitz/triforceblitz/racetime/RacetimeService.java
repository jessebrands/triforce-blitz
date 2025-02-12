package com.triforceblitz.triforceblitz.racetime;

import com.triforceblitz.triforceblitz.racetime.errors.RaceNotFoundException;
import com.triforceblitz.triforceblitz.racetime.race.Race;

public interface RacetimeService {
    Race getRace(String category, String name) throws RaceNotFoundException;
}
