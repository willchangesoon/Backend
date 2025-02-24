package com.es3.order.order.domain;

import com.es3.es3backend.config.exception.OrderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderItemTest {

    OrderItem orderItem;

    @BeforeEach
    void setUp() {
        orderItem = OrderItem.createOrderItem(null, 2, BigDecimal.valueOf(10_000));
    }

    @Test
    void cancel() {
        orderItem.cancel();

        assertThat(orderItem.isCancelled()).isTrue();
    }

    @Test
    void alreadyCancel() {
        orderItem.cancel();

        assertThatThrownBy(() -> orderItem.cancel())
                .isInstanceOf(OrderException.class)
                .message()
                .isEqualTo("이미 취소된 상품입니다.");
    }
}