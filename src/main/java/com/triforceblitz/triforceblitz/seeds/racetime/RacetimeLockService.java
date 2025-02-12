package com.triforceblitz.triforceblitz.seeds.racetime;

import com.triforceblitz.triforceblitz.racetime.RacetimeClient;
import com.triforceblitz.triforceblitz.racetime.RacetimeService;
import com.triforceblitz.triforceblitz.racetime.errors.RaceNotFoundException;
import com.triforceblitz.triforceblitz.racetime.race.Race;
import com.triforceblitz.triforceblitz.seeds.SeedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RacetimeLockService {
    private static final Logger log = LoggerFactory.getLogger(RacetimeLockService.class);

    private final RacetimeService racetimeService;
    private final RacetimeLockRepository lockRepository;
    private final SeedRepository seedRepository;

    public RacetimeLockService(RacetimeService racetimeService,
                               RacetimeLockRepository lockRepository,
                               SeedRepository seedRepository) {
        this.racetimeService = racetimeService;
        this.lockRepository = lockRepository;
        this.seedRepository = seedRepository;
    }

    @Transactional
    public void lockSpoilerLog(UUID id, String category, String raceSlug)
            throws RaceNotFoundException, InvalidRaceException {
        var race = racetimeService.getRace(category, raceSlug);
        if (!race.isOpen()) {
            throw new InvalidRaceException("the race is not open");
        }
        var seed = seedRepository.findById(id).orElseThrow();
            var lock = new RacetimeLock(raceSlug, seed);
            seed.lockSpoiler();
            seedRepository.save(seed);
            lockRepository.save(lock);
    }

    private void checkUnlock(RacetimeLock lock) {
        var seed = lock.getSeed();
        try {
            log.info("Checking Racetime.gg race {}", lock.getRaceSlug());
            var race = racetimeService.getRace("ootr", lock.getRaceSlug());
            if (race.completed()) {
                log.info("Race is complete, unlocking seed {}", seed.getId());
                seed.unlockSpoiler();
                seedRepository.save(seed);
            }
        } catch (RaceNotFoundException e) {
            log.error("The race ootr/{} no longer exists, unlocking seed {}",
                    lock.getRaceSlug(),
                    seed.getId());
            seed.unlockSpoiler();
            seedRepository.save(seed);
            lockRepository.delete(lock);
        }
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void checkUnlocks() {
        var locks = lockRepository.findAllLockedLocks();
        for (var lock : locks) {
            checkUnlock(lock);
        }
    }
}
