package com.triforceblitz.triforceblitz.racetime.race;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RaceGoal {
    @JsonProperty("name")
    private String name;

    @JsonProperty("custom")
    private boolean custom;
}
