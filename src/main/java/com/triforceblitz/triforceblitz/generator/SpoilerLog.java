package com.triforceblitz.triforceblitz.generator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpoilerLog {
    @JsonProperty(":version")
    private String version;

    @JsonProperty("file_hash")
    private List<String> hash;

    @JsonProperty(":seed")
    private String seed;

    @JsonProperty(":settings_string")
    private String settings;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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