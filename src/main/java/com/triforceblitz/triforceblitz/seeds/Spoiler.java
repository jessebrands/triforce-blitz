package com.triforceblitz.triforceblitz.seeds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
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
}
