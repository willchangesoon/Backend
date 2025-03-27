package com.es3.order.config.exception;

public class CartException extends AbstractException{
    public CartException(ErrorCode errorCode) {
        super(errorCode);
    }
}
