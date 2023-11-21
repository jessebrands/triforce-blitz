package com.triforceblitz.triforceblitz.generator;

import com.triforceblitz.triforceblitz.TriforceBlitzVersion;
import com.triforceblitz.triforceblitz.seeds.Seed;

import java.util.Set;

public interface GeneratorService {
    Set<TriforceBlitzVersion> getAvailableVersions();

    Seed generateSeed(TriforceBlitzVersion version, String seed) throws Exception;
}
