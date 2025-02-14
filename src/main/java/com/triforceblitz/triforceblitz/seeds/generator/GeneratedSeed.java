package com.triforceblitz.triforceblitz.seeds.generator;

import java.nio.file.Path;
import java.time.Instant;

public record GeneratedSeed(
        Path spoilerLogFilename,
        Path patchFilename,
        Instant generatedAt
) {

}
