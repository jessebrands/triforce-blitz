package com.triforceblitz.triforceblitz.seeds;

import com.triforceblitz.triforceblitz.Version;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Seed {
    private final UUID id;
    private final Version version;
    private final Season season;
    private final String seed;
    private final Instant requestedOn;

    /**
     * The timestamp at which this seed finished generation.
     */
    @Nullable
    private Instant generatedOn;

    /**
     * The version of the Ocarina of Time randomizer that was used to generate the seed. This is not the same thing as
     * the release version of Triforce Blitz which can be found in the {@link Seed#version} property.
     */
    @Nullable
    private String generatorVersion;

    /**
     * The unique hash for this seed. The seed hash is represented by the name of one of Ocarina of Time's many items,
     * there are five items in the hash total.
     */
    @Nullable
    private List<String> hash = new ArrayList<>();

    /**
     * The settings string used to generate the seed. This string is an alphanumeric representation of the settings
     * selected by the preset, and has nothing to do with the settings in Settings.json.
     */
    @Nullable
    private String settings;

    private boolean spoilerUnlocked = true;

    Seed(UUID id, Version version, Season season, String seed, Instant requestedOn) {
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

    @Nullable
    public Instant getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(@Nullable Instant generatedOn) {
        this.generatedOn = generatedOn;
    }

    @Nullable
    public String getGeneratorVersion() {
        return generatorVersion;
    }

    public void setGeneratorVersion(@Nullable String generatorVersion) {
        this.generatorVersion = generatorVersion;
    }

    @Nullable
    public List<String> getHash() {
        return hash;
    }

    public void setHash(@Nullable List<String> hash) {
        this.hash = hash;
    }

    @Nullable
    public String getSettings() {
        return settings;
    }

    public void setSettings(@Nullable String settings) {
        this.settings = settings;
    }

    public boolean isSpoilerUnlocked() {
        return spoilerUnlocked;
    }

    public void setSpoilerUnlocked(boolean spoilerUnlocked) {
        this.spoilerUnlocked = spoilerUnlocked;
    }
}
