package com.es3.order.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //auth exception
    REGISTERED_EMAIL(HttpStatus.NOT_ACCEPTABLE, 1000, "이미 등록된 이메일 입니다."),
    REGISTERED_MOBILE(HttpStatus.NOT_ACCEPTABLE, 1001, "이미 등록된 모바일 입니다."),
    INVALID_EMAIL(HttpStatus.NOT_ACCEPTABLE, 1002, "잘못된 이메일 입니다."),
    INVALID_PASSWORD(HttpStatus.NOT_ACCEPTABLE, 1003, "잘못된 비밀번호 입니다."),
    SAME_PASSWORD(HttpStatus.NOT_ACCEPTABLE, 1004, "같은 비밀번호 입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 1005, "존재하지 않는 사용자 입니다."),
    INVALID_CREDENTIAL(HttpStatus.NOT_ACCEPTABLE, 1006, "일치하지 않는 정보입니다."),
    //store exception
    REGISTERED_NAME(HttpStatus.NOT_ACCEPTABLE, 2001, "이미 사용중인 이름입니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, 2002, "상점이 존재하지 않습니다."),
    NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED, 2003, "셀러(판매자) 계정으로만 상점을 개설 할 수 있습니다."),
    NOT_AUTHORIZED_READ(HttpStatus.UNAUTHORIZED, 2004, "조회가 불가능한 상점입니다."),
    //banner exception
    DATE_VALIDATION(HttpStatus.NOT_ACCEPTABLE, 3001, "시작날짜는 끝나는 날짜보다 늦을 수 잆습니다."),
    BANNER_NOT_FOUND(HttpStatus.NOT_FOUND, 3002, "존재하지 않는 배너입니다."),
    //order exception
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, 4001, "주문 내역이 존재하지 않습니다."),
    ILLEGAL_ORDER_STATE(HttpStatus.NOT_ACCEPTABLE, 4002, "결제 처리 할  수 없는 상태의 주문입니다."),
    //payment exception
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, 5001, "결제 내역이 존재하지 않습니다. "),
    ILLEGAL_PAYMENT_STATE(HttpStatus.NOT_ACCEPTABLE, 5002, "결제 상태를 변경 할 수 없습니다."),
    ALREADY_CANCELLED_ITEM(HttpStatus.NOT_ACCEPTABLE, 5003, "이미 취소된 상품입니다."),
    ORDER_STORE_NOT_FOUND(HttpStatus.NOT_FOUND, 5004, "세부 주문 내역이 없습니다."),
    INVALID_CANCEL_AMOUNT(HttpStatus.NOT_ACCEPTABLE, 5005, "불가능한 환불금액입니다.");


    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    ErrorCode(HttpStatus status, int code, String message) {
        this.httpStatus = status;
        this.code = code;
        this.message = message;
    }
}
