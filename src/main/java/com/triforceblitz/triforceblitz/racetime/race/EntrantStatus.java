package com.triforceblitz.triforceblitz.racetime.race;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EntrantStatus {
    REQUESTED("requested"),
    INVITED("invited"),
    DECLINED("declined"),
    NOT_READY("not_ready"),
    READY("ready"),
    IN_PROGRESS("in_progress"),
    DNF("dnf"),
    DQ("dq"),
    DONE("done");

    private final String value;

    EntrantStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static EntrantStatus ofValue(String value) {
        return switch (value) {
            case "requested" -> EntrantStatus.REQUESTED;
            case "not_ready" -> EntrantStatus.NOT_READY;
            case "ready" -> EntrantStatus.READY;
            default -> throw new IllegalArgumentException();
        };
    }

    public String getValue() {
        return value;
    }
}
