package com.es3.es3backend.config.exception;

public class OrderException extends AbstractException{
    public OrderException(ErrorCode errorCode) {
        super(errorCode);
    }
}
