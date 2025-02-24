package com.es3.order.payment.dto.request;


import com.es3.order.payment.domain.constants.PaymentMethod;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentForm(
        BigDecimal amount,
        PaymentMethod method,
        Long orderId
) {
}
