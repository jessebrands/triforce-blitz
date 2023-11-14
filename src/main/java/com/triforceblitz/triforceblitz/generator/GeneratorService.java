package com.triforceblitz.triforceblitz.generator;

import com.triforceblitz.triforceblitz.seeds.Seed;

import java.util.Optional;

public interface GeneratorService {
    Optional<Generator> findGenerator(String version);
    Seed generateSeed(String version, String seed) throws Exception;
}
