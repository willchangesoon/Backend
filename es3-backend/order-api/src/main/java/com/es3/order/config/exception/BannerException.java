package com.es3.order.config.exception;

public class BannerException extends AbstractException {
    public BannerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
