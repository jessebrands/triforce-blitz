package com.triforceblitz.triforceblitz.seeds;

import java.nio.file.Path;
import java.util.UUID;

public interface SeedService {
    SeedDetails getById(UUID id);

    SeedDetails generateSeed();

    Path getPatchFilename(UUID id);

    Path getSpoilerLogFilename(UUID id);
}
