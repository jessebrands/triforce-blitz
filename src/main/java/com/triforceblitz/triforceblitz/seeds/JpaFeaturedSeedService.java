package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.seeds.generator.GeneratorService;
import com.triforceblitz.triforceblitz.seeds.spoiler.SpoilerLogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class JpaFeaturedSeedService implements FeaturedSeedService {
    private static final Logger log = LoggerFactory.getLogger(JpaFeaturedSeedService.class);

    private final SeedDetailsManager seedManager;
    private final GeneratorService generatorService;
    private final SpoilerLogManager spoilerLogManager;
    private final SeedRepository seedRepository;
    private final FeaturedSeedRepository featuredSeedRepository;

    public JpaFeaturedSeedService(SeedDetailsManager seedManager,
                                  GeneratorService generatorService,
                                  SpoilerLogManager spoilerLogManager,
                                  SeedRepository seedRepository,
                                  FeaturedSeedRepository featuredSeedRepository) {
        this.seedManager = seedManager;
        this.generatorService = generatorService;
        this.spoilerLogManager = spoilerLogManager;
        this.seedRepository = seedRepository;
        this.featuredSeedRepository = featuredSeedRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0 19 * * *")
    public void generateSeedOfTheDay() throws Exception {
        var today = LocalDate.now();
        if (featuredSeedRepository.existsByDateAndDaily(today, true)) {
            log.warn("Seed of the Day for {} already exists, skipping!", today);
            return;
        }
        // First of all, we generate the seed.
        var seed = seedManager.createSeed(true);
        if (today.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
            log.info("Generating Seed of the Week for {}", today);
            createWeeklySeed(seed.getId());
        } else {
            log.info("Generating Seed of the Day for {}", today);
            createDailySeed(seed.getId());
        }
        spoilerLogManager.lockSpoilerLog(seed.getId());
        generatorService.generateSeed(seed.getId());
    }

    @Transactional
    @Scheduled(cron = "0 0 19 * * *")
    public void unlockPreviousDailySeed() {
        log.info("Checking for previous dailies!");
        var previousDailies = featuredSeedRepository.findAllLockedPreviousDailySeeds();
        for (var daily : previousDailies) {
            var seed = daily.getSeed();
            spoilerLogManager.unlockSpoilerLog(seed.getId());
            log.info("Unlocked spoiler log for Seed of the Day {}", daily.getDate());
        }
    }

    @Nullable
    @Override
    public FeaturedSeedDetails getDailySeed() {
        return featuredSeedRepository.findLatestDailySeed()
                .map(FeaturedSeedDetails::from)
                .orElse(null);
    }

    @Nullable
    @Override
    public FeaturedSeedDetails getWeeklySeed() {
        return featuredSeedRepository.findLatestWeeklySeed()
                .map(FeaturedSeedDetails::from)
                .orElse(null);
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
