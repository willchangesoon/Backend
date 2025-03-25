package com.es3.gateway.exception;

public class TokenException extends AbstractException{
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
