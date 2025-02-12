package com.triforceblitz.triforceblitz.racetime.race;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RaceStatus {
    OPEN("open"),
    INVITATIONAL("invitational"),
    PENDING("pending"),
    IN_PROGRESS("in_progress"),
    FINISHED("finished"),
    CANCELLED("cancelled");

    private final String value;

    RaceStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static RaceStatus ofValue(String value) {
        return switch (value) {
            case "open" -> RaceStatus.OPEN;
            case "invitational" -> RaceStatus.INVITATIONAL;
            case "pending" -> RaceStatus.PENDING;
            case "in_progress" -> RaceStatus.IN_PROGRESS;
            case "finished" -> RaceStatus.FINISHED;
            case "cancelled" -> RaceStatus.CANCELLED;
            default -> throw new IllegalArgumentException();
        };
    }

    public String getValue() {
        return value;
    }
}
