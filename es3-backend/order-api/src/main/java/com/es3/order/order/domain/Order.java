package com.es3.order.order.domain;

import com.es3.order.common.entity.BaseEntity;
import com.es3.order.config.exception.ErrorCode;
import com.es3.order.config.exception.OrderException;
import com.es3.order.order.domain.constants.OrderStatus;
import com.es3.order.payment.domain.Payment;
import com.es3.order.payment.domain.constants.PaymentMethod;
import com.es3.order.payment.domain.constants.PaymentStatus;
import com.es3.order.store.domain.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderStore> orderStores = new ArrayList<>();

    @Column(name = "order_status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    private Long userId;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    public static Order createOrder(Long userId) {
        return new Order(null, new ArrayList<>(), OrderStatus.PENDING, userId, null);
    }

    public void initPayment(PaymentMethod method) {
        payment = Payment.createPayment(method, this, calculateTotalAmount());

    }

    public OrderStore addOrderStore(Store store) {
        OrderStore orderStore = OrderStore.createOrderStore(store, this);
        this.orderStores.add(orderStore);
        return orderStore;
    }

    public boolean isPaymentSuccess() {
        return this.payment.isSuccess();
    }

    public void completePayment(boolean success) {
        if (!this.orderStatus.equals(OrderStatus.PENDING)) {
            throw new OrderException(ErrorCode.ILLEGAL_ORDER_STATE);
        }
        if (success) {
            payment.complete();
            orderStatus = OrderStatus.PAID;
        } else {
            payment.fail();
            orderStatus = OrderStatus.PENDING;
        }
        for (OrderStore store : orderStores) {
            store.completePayment(success);
        }
    }

    public PaymentStatus getPaymentStatus() {
        return this.payment.getStatus();
    }

    public BigDecimal calculateTotalAmount() {
        return this.orderStores.stream()
                .map(OrderStore::calculateSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 부분 취소 수행
    public void cancel(OrderStore store, List<Long> itemIds) {
        // 부분 취소 금액 계산 및 처리
        BigDecimal cancelAmount = store.cancelItems(itemIds);

        // Payment에서 부분 결제 취소 수행
        payment.cancel(cancelAmount);

        // 전체 취소 여부 확인 및 업데이트
        if (isAllOrderStoresCancelled()) {
            this.orderStatus = OrderStatus.CANCELLED;
        }
    }

    private boolean isAllOrderStoresCancelled() {
        return orderStores.stream().allMatch(OrderStore::isAllItemsCancelled);
    }

    public OrderStore findOrderStoreByOrderItemId(Long orderItemId) {
        return orderStores.stream()
                .filter(store -> store.getOrderItems().stream()
                        .anyMatch(item -> item.getId().equals(orderItemId)))
                .findFirst()
                .orElseThrow(() -> new OrderException(ErrorCode.ORDER_STORE_NOT_FOUND));
    }

    public List<OrderItem> getOrderItems() {
        return orderStores.stream()
                .flatMap(orderStore -> orderStore.getOrderItems().stream())
                .toList();
    }
}
