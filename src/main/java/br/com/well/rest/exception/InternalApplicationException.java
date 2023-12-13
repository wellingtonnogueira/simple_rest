package br.com.well.rest.exception;

public class InternalApplicationException extends RuntimeException {

    public InternalApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalApplicationException(String message) {
        super(message);
    }
}