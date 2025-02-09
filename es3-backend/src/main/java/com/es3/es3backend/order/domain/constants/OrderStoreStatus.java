package com.es3.es3backend.order.domain.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStoreStatus {
    PENDING,            //주문 접수 대기중
    ORDER_RECEIVED,      // 주문 접수 완료
    PREPARING_SHIPMENT,  // 발송 준비중
    SHIPPED,             // 발송됨
    OUT_FOR_DELIVERY,    // 배달 중
    DELIVERED,           // 배달 완료
    ORDER_COMPLETED,     // 주문 완료
    CANCELLED             //주문 취소
}
