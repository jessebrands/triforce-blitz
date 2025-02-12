package com.triforceblitz.triforceblitz.seeds.racetime;

import com.triforceblitz.triforceblitz.racetime.RacetimeClient;
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

    private final RacetimeClient racetimeClient;
    private final RacetimeLockRepository lockRepository;
    private final SeedRepository seedRepository;

    public RacetimeLockService(RacetimeClient racetimeClient,
                               RacetimeLockRepository lockRepository,
                               SeedRepository seedRepository) {
        this.racetimeClient = racetimeClient;
        this.lockRepository = lockRepository;
        this.seedRepository = seedRepository;
    }

    @Transactional
    public void lockSpoilerLog(UUID id, String category, String raceSlug) {
        var seed = seedRepository.findById(id).orElseThrow();
        var lock = new RacetimeLock(raceSlug, seed);
        seed.lockSpoiler();
        seedRepository.save(seed);
        lockRepository.save(lock);
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void checkUnlocks() {
        var locks = lockRepository.findAllLockedLocks();
        for (var lock : locks) {
            log.info("Checking Racetime.gg race {}", lock.getRaceSlug());
            var response = racetimeClient.getRace("ootr", lock.getRaceSlug());
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.warn("Could not get race ootr/{}: HTTP error {}",
                        lock.getRaceSlug(),
                        response.getStatusCode().value());
                continue;
            }
            var race = response.getBody();
            if (race == null) {
                log.warn("Got a HTTP 2xx successful response, but Race is null!");
                continue;
            }
            if (race.completed()) {
                var seed = lock.getSeed();
                log.info("Race is complete, unlocking seed {}", seed.getId());
                seed.unlockSpoiler();
                seedRepository.save(seed);
            }
        }
    }
}
