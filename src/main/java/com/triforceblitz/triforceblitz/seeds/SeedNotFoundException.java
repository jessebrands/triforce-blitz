package com.triforceblitz.triforceblitz.seeds;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SeedNotFoundException extends Exception {
    public SeedNotFoundException(String message) {
        super(message);
    }
}
