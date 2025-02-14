package com.triforceblitz.triforceblitz.seeds.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
public class SeedNotGeneratedException extends Exception {
    public SeedNotGeneratedException(String message) {
        super(message);
    }

    public SeedNotGeneratedException(Exception cause) {
        super(cause);
    }
}
