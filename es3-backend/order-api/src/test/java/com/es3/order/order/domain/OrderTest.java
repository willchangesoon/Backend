package com.es3.order.order.domain;

import com.es3.es3backend.order.domain.constants.OrderStatus;
import com.es3.es3backend.order.domain.constants.OrderStoreStatus;
import com.es3.es3backend.payment.domain.constants.PaymentMethod;
import com.es3.es3backend.payment.domain.constants.PaymentStatus;
import com.es3.es3backend.store.domain.Store;
import com.es3.es3backend.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTest {
    private Order order;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .email("test@test.com").build();
        Store store1 = Store.builder().name("store1").build();
        Store store2 = Store.builder().name("store2").build();
        order = Order.createOrder(user);
        OrderStore orderStore1 = order.addOrderStore(store1);
        OrderStore orderStore2= order.addOrderStore(store2);
        orderStore1.addOrderItem(2, BigDecimal.valueOf(300));
        orderStore2.addOrderItem(1, BigDecimal.valueOf(1000));
        order.initPayment(PaymentMethod.CARD);
    }

    @Test
    void testCompletePaymentSuccess() {
        order.completePayment(true);

        assertAll(() -> assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PAID),
                () -> assertThat(order.getPaymentStatus()).isEqualTo(PaymentStatus.SUCCEED),
                () -> assertThat(order.isPaymentSuccess()).isTrue());

        for(OrderStore store: order.getOrderStores()) {
            assertThat(store.getOrderStoreStatus()).isEqualTo(OrderStoreStatus.ORDER_RECEIVED);
        }
    }

    @Test
    void testCompletePaymentFailed() {
        order.completePayment(false);

        assertAll(() -> assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PENDING),
                () -> assertThat(order.getPaymentStatus()).isEqualTo(PaymentStatus.FAILED),
                () -> assertThat(order.isPaymentSuccess()).isFalse());

        for(OrderStore store: order.getOrderStores()) {
            assertThat(store.getOrderStoreStatus()).isEqualTo(OrderStoreStatus.PENDING);
        }
    }

    @Test
    void testCompletePaymentException() {
        order.completePayment(false);

        assertThatThrownBy(() -> order.completePayment(true)).message().isEqualTo("결제 상태를 변경 할 수 없습니다.");
    }


//    @Test
//    void testOrderCancel() {
//        order.completePayment(true);
//        order.cancel();
//
//        assertAll(
//                () -> assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED),
//                () -> assertThat(order.getPaymentStatus()).isEqualTo(PaymentStatus.REFUNDED)
//        );
//    }

}