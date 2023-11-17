package com.triforceblitz.triforceblitz.seeds.websocket;

public class GeneratorStartMessage {
    private long timestamp;

    public GeneratorStartMessage(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
