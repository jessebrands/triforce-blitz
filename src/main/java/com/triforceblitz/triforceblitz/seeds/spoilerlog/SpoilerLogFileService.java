package com.triforceblitz.triforceblitz.seeds.spoilerlog;

import java.nio.file.Path;
import java.util.UUID;

public interface SpoilerLogFileService {
    Path getSpoilerLogPath(UUID seedId) throws NoSpoilerLogException;
}
