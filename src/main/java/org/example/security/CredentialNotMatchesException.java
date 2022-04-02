package org.example.security;

public class CredentialNotMatchesException extends AuthenticationException {
    public CredentialNotMatchesException() {
    }

    public CredentialNotMatchesException(String message) {
        super(message);
    }

    public CredentialNotMatchesException(String message, Throwable cause) {
        super(message, cause);
    }

    public CredentialNotMatchesException(Throwable cause) {
        super(cause);
    }

    public CredentialNotMatchesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
