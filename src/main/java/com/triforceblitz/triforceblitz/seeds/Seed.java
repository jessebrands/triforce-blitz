package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.Version;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.UUID;

public class Seed {
    private final UUID id;
    private final Version version;
    private final Season season;
    private final String seed;
    private final Instant requestedOn;

    public Seed(UUID id, Version version, Season season, String seed, Instant requestedOn) {
        this.id = id;
        this.version = version;
        this.season = season;
        this.seed = seed;
        this.requestedOn = requestedOn;
    }

    public static Seed create(Version version, Season season, String seed) {
        return new Seed(
                UUID.randomUUID(),
                version,
                season,
                seed,
                Instant.now()
        );
    }

    @Nullable
    private Instant generatedOn;

    public UUID getId() {
        return id;
    }

    public Version getVersion() {
        return version;
    }

    public Season getSeason() {
        return season;
    }

    public String getSeed() {
        return seed;
    }

    public Instant getRequestedOn() {
        return requestedOn;
    }
}
