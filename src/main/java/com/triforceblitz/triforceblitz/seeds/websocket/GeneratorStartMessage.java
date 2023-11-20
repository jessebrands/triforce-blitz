package com.triforceblitz.triforceblitz.seeds.websocket;

import lombok.Data;

@Data
public class GeneratorStartMessage {
    private long timestamp;

    public GeneratorStartMessage(long timestamp) {
        this.timestamp = timestamp;
    }
}
