package com.es3.es3backend.payment.domain;

import com.es3.es3backend.common.entity.BaseEntity;
import com.es3.es3backend.order.domain.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private PaymentMethod paymentMethod;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "status")
    private PaymentStatus status;

    @OneToOne(mappedBy = "payment")
    private Order order;
}
