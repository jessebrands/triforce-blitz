package com.triforceblitz.triforceblitz.seeds.racetime;

import com.triforceblitz.triforceblitz.racetime.race.Race;
import com.triforceblitz.triforceblitz.racetime.race.RaceStatus;

import java.util.UUID;

public class JpaRacetimeLockDetails implements RacetimeLockDetails {
    private final RacetimeLock lock;
    private final Race race;

    public JpaRacetimeLockDetails(RacetimeLock lock, Race race) {
        this.lock = lock;
        this.race = race;
    }

    @Override
    public UUID getSeedId() {
        return lock.getSeed().getId();
    }

    @Override
    public boolean isLocked() {
        return lock.getSeed().isSpoilerLocked();
    }

    @Override
    public String getRaceCategory() {
        return race.getCategory().getSlug();
    }

    @Override
    public String getRaceName() {
        return race.getSlug();
    }

    @Override
    public boolean isOpen() {
        return race.isOpen();
    }

    @Override
    public boolean isInProgress() {
        return race.getStatus() == RaceStatus.IN_PROGRESS;
    }

    @Override
    public boolean isFinished() {
        return race.completed();
    }
}
