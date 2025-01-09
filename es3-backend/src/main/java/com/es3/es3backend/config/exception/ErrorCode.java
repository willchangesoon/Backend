package com.es3.es3backend.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    REGISTERED_EMAIL(HttpStatus.NOT_ACCEPTABLE, "2001", "이미 등록된 이메일 입니다."),
    INVALID_EMAIL(HttpStatus.NOT_ACCEPTABLE, "2002", "잘못된 이메일 입니다."),
    INVALID_PASSWORD(HttpStatus.NOT_ACCEPTABLE, "2003", "잘못된 비밀번호 입니다."),
    SAME_PASSWORD(HttpStatus.NOT_ACCEPTABLE, "2004", "같은 비밀번호 입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_ACCEPTABLE, "2005", "존재하지 않는 사용자 입니다."),
    INVALID_CREDENTIAL(HttpStatus.NOT_ACCEPTABLE, "2006", "일치하지 않는 정보입니다."),
    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status,  String code, String message) {
        this.httpStatus = status;
        this.code = code;
        this.message = message;
    }
}
