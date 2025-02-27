package com.es3.order.config.exception;

public class OrderException extends AbstractException {
    public OrderException(ErrorCode errorCode) {
        super(errorCode);
    }
}
