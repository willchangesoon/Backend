package com.es3.order.config.exception;

public class StoreException extends AbstractException {
    public StoreException(ErrorCode errorCode) {
        super(errorCode);
    }
}
