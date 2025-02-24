package com.es3.order.config.exception;

public class PaymentException extends AbstractException {
    public PaymentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
