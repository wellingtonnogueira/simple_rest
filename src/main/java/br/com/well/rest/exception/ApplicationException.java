package br.com.well.rest.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    private final HttpStatus defaultHttpStatus;

    public ApplicationException(String message, Throwable cause, HttpStatus defaultError) {
        super(message, cause);
        this.defaultHttpStatus = defaultError;
    }

    public final HttpStatus getDefaultHttpStatus() {
        return defaultHttpStatus;
    }

}