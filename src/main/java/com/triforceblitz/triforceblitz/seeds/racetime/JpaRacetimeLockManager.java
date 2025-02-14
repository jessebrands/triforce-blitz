package com.triforceblitz.triforceblitz.seeds.racetime;

import com.triforceblitz.triforceblitz.racetime.RacetimeService;
import com.triforceblitz.triforceblitz.racetime.errors.RaceNotFoundException;
import com.triforceblitz.triforceblitz.seeds.Seed;
import com.triforceblitz.triforceblitz.seeds.SeedRepository;
import com.triforceblitz.triforceblitz.seeds.errors.SeedNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class JpaRacetimeLockManager implements RacetimeLockManager {
    private static final Logger log = LoggerFactory.getLogger(JpaRacetimeLockManager.class);

    private final RacetimeService racetimeService;
    private final SeedRepository seedRepository;

    public JpaRacetimeLockManager(RacetimeService racetimeService,
                                  SeedRepository seedRepository) {
        this.racetimeService = racetimeService;
        this.seedRepository = seedRepository;
    }

    private void checkLockStatus(Seed seed) {
        var lock = seed.getRacetimeLock();
        assert lock != null;
        try {
            log.info("Checking Racetime.gg race {}/{}", lock.getRaceCategory(), lock.getRaceSlug());
            var race = racetimeService.getRace(lock.getRaceCategory(), lock.getRaceSlug());
            if (race.completed()) {
                log.info("Race is complete, unlocking seed {}", seed.getId());
                seed.setSpoilerLocked(false);
                seedRepository.save(seed);
            }
        } catch (RaceNotFoundException e) {
            log.warn("The race {}/{} no longer exists, unlocking seed {}",
                    lock.getRaceCategory(),
                    lock.getRaceSlug(),
                    seed.getId());

            seed.removeRacetimeLock();
            seed.setSpoilerLocked(false);
            seedRepository.save(seed);
        }
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void checkUnlocks() {
        var seeds = seedRepository.findAllRacetimeLockedSeeds();
        for (var seed : seeds) {
            checkLockStatus(seed);
        }
    }

    @Override
    @Transactional
    public void lockSpoilerLogWithRace(UUID id, String category, String raceSlug)
            throws RaceNotFoundException, InvalidRaceException, SeedNotFoundException {
        var race = racetimeService.getRace(category, raceSlug);
        if (!race.isOpen()) {
            throw new InvalidRaceException("the race is not open");
        }
        var seed = seedRepository.findById(id)
                .orElseThrow(() -> new SeedNotFoundException("seed not found"));

        seed.addRacetimeLock(category, raceSlug);
        seed.setSpoilerLocked(true);
        seedRepository.save(seed);
    }

    @Override
    public RacetimeLockDetails findRacetimeLockBySeedId(UUID seedId) throws NotLockedException, RaceNotFoundException {
        var seed = seedRepository.findById(seedId)
                .orElseThrow(() -> new NotLockedException("seed not found"));

        var lock = Optional.ofNullable(seed.getRacetimeLock())
                .orElseThrow(() -> new NotLockedException("not locked"));

        var race = racetimeService.getRace(lock.getRaceCategory(), lock.getRaceSlug());
        return new JpaRacetimeLockDetails(lock, race);
    }
}
