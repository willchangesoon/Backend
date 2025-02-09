package com.es3.es3backend.order.domain;

import com.es3.es3backend.common.entity.BaseEntity;
import com.es3.es3backend.order.domain.constants.OrderStatus;
import com.es3.es3backend.payment.domain.Payment;
import com.es3.es3backend.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "order")
    private List<OrderStore> orderStores = new ArrayList<>();

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "order_status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @Builder
    public Order(BigDecimal totalPrice, OrderStatus orderStatus, User user) {
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.user = user;
    }

    public void addOrderStore(OrderStore orderStore) {
        this.orderStores.add(orderStore);
    }
}
