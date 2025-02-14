package com.triforceblitz.triforceblitz.seeds.racetime;

import com.triforceblitz.triforceblitz.racetime.errors.RaceNotFoundException;

import java.util.UUID;

public interface RacetimeLockDetailsService {
    RacetimeLockDetails findRacetimeLockBySeedId(UUID seedId) throws NotLockedException, RaceNotFoundException;
}
