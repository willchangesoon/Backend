package com.es3.order.payment.domain.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentMethod {
    CARD,
    BANK_TRANSFER,
    COD
}
