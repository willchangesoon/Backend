package com.es3.es3backend.payment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentStatus {
    FAILED,
    CANCELED,
    SUCCEED
}
