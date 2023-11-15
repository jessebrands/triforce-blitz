package com.triforceblitz.triforceblitz.seeds;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Season implements Comparable<Season> {
    private final UUID uuid = UUID.randomUUID();
    private final int ordinal;
    private final String name;
    private String preset;
    private String messageKey;

    @Override
    public int compareTo(Season o) {
        return Integer.compare(ordinal, o.ordinal);
    }

    public Season(int ordinal, String name, String preset) {
        this.ordinal = ordinal;
        this.name = name;
        this.preset = preset;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public String getName() {
        return name;
    }

    public String getMessageKey() {
        return messageKey;
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
}
