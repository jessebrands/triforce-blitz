package com.triforceblitz.triforceblitz.generator.websocket;

public class GeneratorStatusMessage {
    private String message;

    public GeneratorStatusMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
