package com.triforceblitz.triforceblitz.seeds.websocket;

import lombok.Data;

@Data
public class GeneratorSuccessMessage {
    private long timestamp;

    public GeneratorSuccessMessage(long timestamp) {
        this.timestamp = timestamp;
    }
}
