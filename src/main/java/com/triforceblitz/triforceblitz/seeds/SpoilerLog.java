package com.triforceblitz.triforceblitz.seeds;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SpoilerLog {
    @JsonProperty(":version")
    private String randomizerVersion;

    @JsonProperty("file_hash")
    private List<String> hash;

    @JsonProperty(":seed")
    private String seed;

    @JsonProperty(":settings_string")
    private String settingsString;

    public String getRandomizerVersion() {
        return randomizerVersion;
    }

    public List<String> getHash() {
        return hash;
    }

    public String getSeed() {
        return seed;
    }

    public String getSettingsString() {
        return settingsString;
    }
}
