package com.es3.user.config.exception;

public class PaymentException extends AbstractException{
    public PaymentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
