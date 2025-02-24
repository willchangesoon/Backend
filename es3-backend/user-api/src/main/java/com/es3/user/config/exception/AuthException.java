package com.es3.user.config.exception;

public class AuthException extends AbstractException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
