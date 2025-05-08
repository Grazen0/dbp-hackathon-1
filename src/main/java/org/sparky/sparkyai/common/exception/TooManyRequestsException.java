package org.sparky.sparkyai.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TooManyRequestsException extends ResponseStatusException {

    public TooManyRequestsException(String message) {
        super(HttpStatus.TOO_MANY_REQUESTS, message);
    }

    public TooManyRequestsException() {
        this("Too many requests");
    }

}
