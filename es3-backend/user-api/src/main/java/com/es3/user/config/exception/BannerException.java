package com.es3.user.config.exception;

public class BannerException extends AbstractException{
    public BannerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
