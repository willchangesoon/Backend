package com.es3.es3backend.config.exception;

public class BannerException extends AbstractException{
    public BannerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
