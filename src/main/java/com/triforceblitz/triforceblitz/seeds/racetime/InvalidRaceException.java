package com.triforceblitz.triforceblitz.seeds.racetime;

/**
 * Throw when the race state is invalid for the operation.
 */
public class InvalidRaceException extends Exception {
    public InvalidRaceException(String message) {
        super(message);
    }
}
