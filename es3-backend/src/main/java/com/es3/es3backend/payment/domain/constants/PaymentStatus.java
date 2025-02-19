package com.es3.es3backend.payment.domain.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentStatus {
    PENDING,    //결제 진행중
    FAILED,     //결제 실패
    CANCELED,   //결제 취소
    SUCCEED,    //결제 성공
    TIME_OUT,   //결제 중단
    REFUNDED,   //환불
    PARTIALLY_REFUND    //부분환불
}
