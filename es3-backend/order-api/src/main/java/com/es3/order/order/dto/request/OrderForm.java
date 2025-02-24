package com.es3.order.order.dto.request;

import com.es3.order.payment.domain.constants.PaymentMethod;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderForm(
        List<Long> storeIds,
        List<OrderItemForm> items,
        PaymentMethod paymentMethod
) {
}
