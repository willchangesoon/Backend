package com.es3.order.config.exception;

public class AuthException extends AbstractException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
