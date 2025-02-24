package com.es3.user.config.exception;

public class StoreException extends AbstractException {
    public StoreException(ErrorCode errorCode) {
        super(errorCode);
    }
}
