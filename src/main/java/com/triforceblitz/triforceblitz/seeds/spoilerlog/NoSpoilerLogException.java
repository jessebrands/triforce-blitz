package com.triforceblitz.triforceblitz.seeds.spoilerlog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSpoilerLogException extends Exception {
    public NoSpoilerLogException(String message) {
        super(message);
    }
}
