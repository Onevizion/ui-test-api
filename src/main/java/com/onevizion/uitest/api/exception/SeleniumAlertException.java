package com.onevizion.uitest.api.exception;

public class SeleniumAlertException extends RuntimeException {

    public SeleniumAlertException() {
        super();
    }

    public SeleniumAlertException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeleniumAlertException(String message) {
        super(message);
    }

    public SeleniumAlertException(Throwable cause) {
        super(cause);
    }

}