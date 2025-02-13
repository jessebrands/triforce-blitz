package com.triforceblitz.triforceblitz.seeds;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public interface SeedService {
    Optional<SeedDetails> getById(UUID id);

    SeedDetails createSeed(boolean cooperative);

    void generateSeed(UUID id);

    void lockSpoilerLog(UUID id);

    void unlockSpoilerLog(UUID id);

    Path getPatchFilename(UUID id);

    Path getSpoilerLogFilename(UUID id);

    SpoilerLog getSpoilerLog(UUID id);

    void deleteSeed(UUID id);
}
