package com.triforceblitz.triforceblitz.generator;

import com.triforceblitz.triforceblitz.Version;
import com.triforceblitz.triforceblitz.seeds.Season;
import com.triforceblitz.triforceblitz.seeds.Seed;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GeneratorService {
    Optional<Generator> findGenerator(Version version);

    Seed generateSeed(Version version, Season season, String seed) throws Exception;

    Set<Version> getAvailableVersions();

    List<Season> getCompatibleSeasons(Version version);

    boolean isSeasonCompatible(Version version, Season season);
}
