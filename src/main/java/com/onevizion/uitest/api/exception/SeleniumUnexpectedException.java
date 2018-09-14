package com.onevizion.uitest.api.exception;

public class SeleniumUnexpectedException extends RuntimeException {

    public SeleniumUnexpectedException() {
        super();
    }

    public SeleniumUnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeleniumUnexpectedException(String message) {
        super(message);
    }

    public SeleniumUnexpectedException(Throwable cause) {
        super(cause);
    }

}