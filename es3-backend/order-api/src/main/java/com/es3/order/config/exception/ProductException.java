package com.es3.order.config.exception;

public class ProductException extends AbstractException{
    public ProductException(ErrorCode errorCode) {
        super(errorCode);
    }
}
