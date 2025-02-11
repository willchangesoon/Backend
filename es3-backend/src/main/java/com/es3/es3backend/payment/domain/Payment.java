package com.es3.es3backend.payment.domain;

import com.es3.es3backend.common.entity.BaseEntity;
import com.es3.es3backend.order.domain.Order;
import com.es3.es3backend.order.domain.OrderStore;
import com.es3.es3backend.order.domain.constants.OrderStatus;
import com.es3.es3backend.order.domain.constants.OrderStoreStatus;
import com.es3.es3backend.payment.domain.constants.PaymentMethod;
import com.es3.es3backend.payment.domain.constants.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "tb_payment")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "method")
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Builder
    public Payment(PaymentMethod paymentMethod, BigDecimal amount, PaymentStatus status, Order order) {
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.status = status;
        this.order = order;
    }

    public void paymentComplete() {
        this.status = PaymentStatus.SUCCEED;
        this.order.updateOrderStatus(OrderStatus.PAID);
        for (OrderStore store : this.order.getOrderStores()) {
            store.updateStatus(OrderStoreStatus.ORDER_RECEIVED);
        }
    }

    public void paymentFailed() {
        this.status = PaymentStatus.FAILED;
        this.order.updateOrderStatus(OrderStatus.CANCELLED);
        for (OrderStore store : this.order.getOrderStores()) {
            store.updateStatus(OrderStoreStatus.CANCELLED);
        }
    }
}
