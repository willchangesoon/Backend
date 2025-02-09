package com.es3.es3backend.order.domain.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING,    //주문 접수중
    PAID,       //결제 완료
    CANCELLED,  //주문 취소
    PARTIALLY_CANCELLED //부분 주문 취소
}