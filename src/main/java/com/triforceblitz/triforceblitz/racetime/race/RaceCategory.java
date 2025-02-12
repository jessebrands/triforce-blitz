package com.triforceblitz.triforceblitz.racetime.race;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RaceCategory {
    @JsonProperty("name")
    private String name;

    @JsonProperty("short_name")
    private String shortName;

    @JsonProperty("slug")
    private String slug;

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getSlug() {
        return slug;
    }
}
