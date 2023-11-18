package com.triforceblitz.triforceblitz.seeds;

import java.nio.file.Path;
import java.util.Optional;

public interface SeedService {
    Optional<Path> getPatchFilename(Seed seed);
    Optional<Path> getSpoilerFilename(Seed seed);
}
