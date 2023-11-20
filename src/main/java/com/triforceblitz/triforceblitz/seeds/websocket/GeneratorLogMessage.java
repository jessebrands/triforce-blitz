package com.triforceblitz.triforceblitz.seeds.websocket;

import lombok.Data;

@Data
public class GeneratorLogMessage {
    private String message;
    private long timestamp;

    public GeneratorLogMessage(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
