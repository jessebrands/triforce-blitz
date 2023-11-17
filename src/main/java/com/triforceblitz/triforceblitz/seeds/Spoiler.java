package com.triforceblitz.triforceblitz.seeds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Spoiler {
    @JsonProperty(":version")
    private String randomizerVersion;

    @JsonProperty("file_hash")
    private List<String> hash = new ArrayList<>();

    @JsonProperty(":seed")
    private String seed;

    @JsonProperty(":settings_string")
    private String settings;

    public String getRandomizerVersion() {
        return randomizerVersion;
    }

    public void setRandomizerVersion(String randomizerVersion) {
        this.randomizerVersion = randomizerVersion;
    }

    public List<String> getHash() {
        return hash;
    }

    public void setHash(List<String> hash) {
        this.hash = hash;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }
}
