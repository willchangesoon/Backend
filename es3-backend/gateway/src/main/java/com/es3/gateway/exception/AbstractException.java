package com.es3.gateway.exception;

import lombok.Getter;

@Getter
public abstract class AbstractException extends RuntimeException{
    private final ErrorCode errorCode;

    public AbstractException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
