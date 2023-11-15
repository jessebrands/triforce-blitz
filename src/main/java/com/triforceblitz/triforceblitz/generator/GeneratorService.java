package com.triforceblitz.triforceblitz.generator;

import com.triforceblitz.triforceblitz.Version;
import com.triforceblitz.triforceblitz.seeds.Season;
import com.triforceblitz.triforceblitz.seeds.Seed;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GeneratorService {
    Optional<Generator> findGenerator(Version version);

    default Seed generateSeed(Version version, Season season, String seed) throws Exception {
        return generateSeed(version,season, seed, null);
    }

    Seed generateSeed(Version version, Season season, String seed, @Nullable String requestId) throws Exception;

    Set<Version> getAvailableVersions();

    List<Season> getCompatibleSeasons(Version version);

    boolean isSeasonCompatible(Version version, Season season);
}
