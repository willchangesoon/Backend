package com.es3.user.config.exception;

public class OrderException extends AbstractException {
    public OrderException(ErrorCode errorCode) {
        super(errorCode);
    }
}
