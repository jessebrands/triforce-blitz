package com.triforceblitz.triforceblitz.racetime.race;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EntrantStatus {
    REQUESTED("requested"),
    INVITED("invited"),
    DECLINED("declined"),
    NOT_READY("not_ready"),
    READY("ready"),
    IN_PROGRESS("in_progress"),
    DID_NOT_FINISH("dnf"),
    DISQUALIFIED("dq"),
    DONE("done");

    private final String value;

    EntrantStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static EntrantStatus ofValue(String value) {
        return switch (value) {
            case "requested" -> EntrantStatus.REQUESTED;
            case "invited" -> EntrantStatus.INVITED;
            case "declined" -> EntrantStatus.DECLINED;
            case "not_ready" -> EntrantStatus.NOT_READY;
            case "ready" -> EntrantStatus.READY;
            case "in_progress" -> EntrantStatus.IN_PROGRESS;
            case "dnf" -> EntrantStatus.DID_NOT_FINISH;
            case "dq" -> EntrantStatus.DISQUALIFIED;
            case "done" -> EntrantStatus.DONE;
            default -> throw new IllegalArgumentException();
        };
    }

    public String getValue() {
        return value;
    }
}
