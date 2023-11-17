package com.triforceblitz.triforceblitz.seeds.websocket;

public class GeneratorSuccessMessage {
    private long timestamp;

    public GeneratorSuccessMessage(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
