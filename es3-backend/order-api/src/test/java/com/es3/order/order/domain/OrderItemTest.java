package com.es3.order.order.domain;

import com.es3.order.config.exception.OrderException;
import com.es3.order.product.domain.Product;
import com.es3.order.product.domain.ProductSKU;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class OrderItemTest {

    OrderItem orderItem;

    @BeforeEach
    void setUp() {
        // 가짜 SKU 생성
        Product product = mock(Product.class);
        ProductSKU sku = new ProductSKU(1L, product, new ArrayList<>(), 100, BigDecimal.ZERO);

        // 테스트 대상 OrderItem 생성
        OrderStore orderStore = mock(OrderStore.class); // null이어도 상관없으면 null 가능
        orderItem = OrderItem.createOrderItem(sku, orderStore, 2, BigDecimal.valueOf(10_000));
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
                .hasMessage("이미 취소된 상품입니다.");
    }
}
