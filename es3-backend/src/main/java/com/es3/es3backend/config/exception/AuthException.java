package com.es3.es3backend.config.exception;

public class AuthException extends AbstractException{
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
