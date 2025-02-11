package com.triforceblitz.triforceblitz.seeds;

import java.nio.file.Path;

public interface SeedService {
    SeedDetails getById(String id);

    SeedDetails generateSeed();

    Path getPatchFilename(String id);

    Path getSpoilerLogFilename(String id);
}
