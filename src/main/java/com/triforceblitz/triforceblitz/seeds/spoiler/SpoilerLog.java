package com.triforceblitz.triforceblitz.seeds.spoiler;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SpoilerLog {
    @JsonProperty(":version")
    private String getVersion;

    @JsonProperty("file_hash")
    private List<String> hash;

    @JsonProperty(":seed")
    private String seed;

    @JsonProperty(":settings_string")
    private String settingsString;

    public String getGetVersion() {
        return getVersion;
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
