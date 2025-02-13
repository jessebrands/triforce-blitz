package com.triforceblitz.triforceblitz.seeds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ScheduledFeaturedSeedService implements FeaturedSeedService {
    private static final Logger log = LoggerFactory.getLogger(ScheduledFeaturedSeedService.class);

    private final SeedService seedService;
    private final SeedRepository seedRepository;
    private final FeaturedSeedRepository featuredSeedRepository;

    public ScheduledFeaturedSeedService(SeedService seedService,
                                        SeedRepository seedRepository,
                                        FeaturedSeedRepository featuredSeedRepository) {
        this.seedService = seedService;
        this.seedRepository = seedRepository;
        this.featuredSeedRepository = featuredSeedRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0 19 * * *")
    public void generateSeedOfTheDay() {
        var today = LocalDate.now();
        if (featuredSeedRepository.existsByDateAndDaily(today, true)) {
            log.warn("Seed of the Day for {} already exists, skipping!", today);
            return;
        }
        // First of all, we generate the seed.
        var seed = seedService.createSeed(true);
        if (today.getDayOfWeek().equals(DayOfWeek.THURSDAY)) {
            log.info("Generating Seed of the Week for {}", today);
            createWeeklySeed(seed.getId());
        } else {
            log.info("Generating Seed of the Day for {}", today);
            createDailySeed(seed.getId());
        }
        seedService.lockSpoilerLog(seed.getId());
        seedService.generateSeed(seed.getId());
    }

    @Transactional
    @Scheduled(cron = "0 0 19 * * *")
    public void unlockPreviousDailySeed() {
        log.info("Checking for previous dailies!");
        var previousDailies = featuredSeedRepository.findAllLockedPreviousDailySeeds();
        for (var daily : previousDailies) {
            var seed = daily.getSeed();
            seedService.unlockSpoilerLog(seed.getId());
            log.info("Unlocked spoiler log for Seed of the Day {}", daily.getDate());
        }
    }

    @Override
    public void createDailySeed(UUID seedId) {
        var seed = seedRepository.findById(seedId).orElseThrow();
        var featuredSeed = new FeaturedSeed(seed);
        featuredSeed.setDaily(true);
        featuredSeedRepository.save(featuredSeed);
    }

    @Override
    public void createWeeklySeed(UUID seedId) {
        var seed = seedRepository.findById(seedId).orElseThrow();
        var featuredSeed = new FeaturedSeed(seed);
        featuredSeed.setDaily(true);
        featuredSeed.setWeekly(true);
        featuredSeedRepository.save(featuredSeed);
    }
}
