package org.example.exception;

public class BadCredentialFormatException extends RuntimeException {
    public BadCredentialFormatException() {
    }

    public BadCredentialFormatException(String message) {
        super(message);
    }

    public BadCredentialFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadCredentialFormatException(Throwable cause) {
        super(cause);
    }

    public BadCredentialFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
