package com.triforceblitz.triforceblitz.seeds;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class Season implements Comparable<Season> {
    private final long id;

    private final int ordinal;
    private String preset;
    private String messageKey;

    @Override
    public int compareTo(Season o) {
        return Integer.compare(ordinal, o.ordinal);
    }

    public Season(long id, int ordinal, String preset, String messageKey) {
        this.id = id;
        this.ordinal = ordinal;
        this.preset = preset;
        this.messageKey = messageKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Season season = (Season) o;
        return id == season.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public long getId() {
        return id;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
