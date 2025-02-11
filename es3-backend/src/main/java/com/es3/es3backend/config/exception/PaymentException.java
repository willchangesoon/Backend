package com.es3.es3backend.config.exception;

public class PaymentException extends AbstractException{
    public PaymentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
