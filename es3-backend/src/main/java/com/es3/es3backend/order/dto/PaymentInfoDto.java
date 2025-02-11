package com.es3.es3backend.order.dto;

import com.es3.es3backend.payment.domain.Payment;
import com.es3.es3backend.payment.domain.constants.PaymentMethod;
import com.es3.es3backend.payment.domain.constants.PaymentStatus;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentInfoDto(
        Long paymentId,
        PaymentMethod method,
        PaymentStatus status,
        BigDecimal amount
) {

    public static PaymentInfoDto fromEntity(Payment payment) {
        return PaymentInfoDto.builder()
                .paymentId(payment.getId())
                .method(payment.getPaymentMethod())
                .status(payment.getStatus())
                .amount(payment.getAmount())
                .build();
    }
}
