package com.es3.es3backend.payment.dto;

import com.es3.es3backend.payment.domain.Payment;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentDto(
        Long orderId,
        BigDecimal amount,
        String result
) {

    public static PaymentDto fromEntity(Payment payment, String result) {
        return PaymentDto.builder()
                .amount(payment.getAmount())
                .orderId(payment.getOrder().getId())
                .result(result)
                .build();
    }
}
