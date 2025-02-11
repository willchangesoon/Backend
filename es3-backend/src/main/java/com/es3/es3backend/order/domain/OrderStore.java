package com.es3.es3backend.order.domain;

import com.es3.es3backend.common.entity.BaseEntity;
import com.es3.es3backend.order.domain.constants.OrderStoreStatus;
import com.es3.es3backend.store.domain.Store;
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
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_order_store")
public class OrderStore extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "orderStore")
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStoreStatus orderStoreStatus;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Builder
    public OrderStore(Store store, Order order, OrderStoreStatus orderStoreStatus, BigDecimal totalPrice) {
        this.store = store;
        this.order = order;
        this.orderStoreStatus = orderStoreStatus;
        this.totalPrice = totalPrice;
        order.addOrderStore(this);
    }

    public void addOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public void updateStatus(OrderStoreStatus orderStoreStatus) {
        this.orderStoreStatus = orderStoreStatus;
    }
}
