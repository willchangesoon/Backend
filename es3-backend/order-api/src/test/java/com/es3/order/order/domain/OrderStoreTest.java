package com.es3.order.order.domain;

import com.es3.es3backend.order.domain.constants.OrderStoreStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderStoreTest {

    OrderStore orderStore;

    @BeforeEach
    void setUp() {
        orderStore = OrderStore.createOrderStore(null, null);
    }

    @Test
    void completePaymentSuccess() {
        orderStore.completePayment(true);

        assertThat(orderStore.getOrderStoreStatus()).isEqualTo(OrderStoreStatus.ORDER_RECEIVED);
    }

    @Test
    void completePaymentFail() {
        orderStore.completePayment(false);

        assertThat(orderStore.getOrderStoreStatus()).isEqualTo(OrderStoreStatus.PENDING);
    }

    @Test
    void prepareShipment() {
        orderStore.completePayment(true);
        orderStore.prepareShipment();

        assertThat(orderStore.getOrderStoreStatus()).isEqualTo(OrderStoreStatus.PREPARING_SHIPMENT);
    }

    @Test
    void shipping() {
        orderStore.completePayment(true);
        orderStore.prepareShipment();
        orderStore.shipping();

        assertThat(orderStore.getOrderStoreStatus()).isEqualTo(OrderStoreStatus.OUT_FOR_DELIVERY);
    }

    @Test
    void delivered() {
        orderStore.completePayment(true);
        orderStore.prepareShipment();
        orderStore.shipping();
        orderStore.delivered();

        assertThat(orderStore.getOrderStoreStatus()).isEqualTo(OrderStoreStatus.DELIVERED);
    }

    @Test
    void complete() {
        orderStore.completePayment(true);
        orderStore.prepareShipment();
        orderStore.shipping();
        orderStore.delivered();
        orderStore.complete();

        assertThat(orderStore.getOrderStoreStatus()).isEqualTo(OrderStoreStatus.ORDER_COMPLETED);
    }
}