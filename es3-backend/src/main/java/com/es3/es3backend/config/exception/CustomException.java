package com.es3.es3backend.config.exception;

public class CustomException extends AbstractException{
    public CustomException(ErrorCode errorCode) {
        super(errorCode);
    }
}
