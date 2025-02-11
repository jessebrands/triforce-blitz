package com.triforceblitz.triforceblitz.seeds;

import java.nio.file.Files;
import java.nio.file.Path;

public class SeedDetails {
    private final String id;
    private final Path location;

    public SeedDetails(String id, Path location) {
        this.id = id;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public Path getLocation() {
        return location;
    }

    public Path patchFile() {
        return location.resolve("TriforceBlitz.zpf");
    }

    public boolean isGenerated() {
        return Files.exists(patchFile());
    }
}
