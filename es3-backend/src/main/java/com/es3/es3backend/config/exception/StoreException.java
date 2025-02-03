package com.es3.es3backend.config.exception;

public class StoreException extends AbstractException{
    public StoreException(ErrorCode errorCode) {
        super(errorCode);
    }
}
