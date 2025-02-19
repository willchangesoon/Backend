package com.es3.es3backend.payment.domain;

import com.es3.es3backend.common.entity.BaseEntity;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.config.exception.PaymentException;
import com.es3.es3backend.order.domain.Order;
import com.es3.es3backend.payment.domain.constants.PaymentMethod;
import com.es3.es3backend.payment.domain.constants.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "remaining_amount")  //환불시 남은 돈
    private BigDecimal remainingAmount;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public static Payment createPayment(PaymentMethod paymentMethod, Order order, BigDecimal totalAmount) {
        return new Payment(null, paymentMethod, totalAmount, BigDecimal.ZERO, PaymentStatus.PENDING, order);
    }

    public void complete() {
        if (!this.status.equals(PaymentStatus.PENDING)) {
            throw new PaymentException(ErrorCode.ILLEGAL_PAYMENT_STATE);
        }
        this.status = PaymentStatus.SUCCEED;
        this.remainingAmount = this.totalAmount;
    }

    public void fail() {
        if (!this.status.equals(PaymentStatus.PENDING)) {
            throw new PaymentException(ErrorCode.ILLEGAL_PAYMENT_STATE);
        }
        this.status = PaymentStatus.FAILED;
    }

    public boolean isSuccess() {
        return status.equals(PaymentStatus.SUCCEED);
    }

    public void cancel(BigDecimal cancelAmount) {
        if (!(status.equals(PaymentStatus.SUCCEED) || status.equals(PaymentStatus.PARTIALLY_REFUND))) {
            throw new PaymentException(ErrorCode.ILLEGAL_PAYMENT_STATE);
        }

        if (cancelAmount.compareTo(remainingAmount) > 0) {
            throw new PaymentException(ErrorCode.INVALID_CANCEL_AMOUNT);
        }

//        // PG사 부분 취소 호출 (더미 메서드)
//        paymentGateway.partialCancel(this, cancelAmount);

        this.remainingAmount = remainingAmount.subtract(cancelAmount);

        if (remainingAmount.compareTo(BigDecimal.ZERO) == 0) {
            this.status = PaymentStatus.REFUNDED;
        } else {
            this.status = PaymentStatus.PARTIALLY_REFUND;
        }
    }
}
