package com.triforceblitz.triforceblitz.generator;

import com.triforceblitz.triforceblitz.seeds.Seed;

import java.util.Optional;
import java.util.Set;

public interface GeneratorService {
    Optional<Generator> findGenerator(String version);
    Seed generateSeed(String version, String seed) throws Exception;
    Set<String> getAvailableVersions();
}
