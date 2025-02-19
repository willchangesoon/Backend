package com.es3.es3backend.payment.domain;

import com.es3.es3backend.config.exception.PaymentException;
import com.es3.es3backend.payment.domain.constants.PaymentMethod;
import com.es3.es3backend.payment.domain.constants.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PaymentTest {
    Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.createPayment(PaymentMethod.CARD, null, BigDecimal.valueOf(30_000));
    }

    @Test
    void testComplete() {
        payment.complete();

        assertAll(
                () -> assertThat(payment.isSuccess()).isTrue(),
                () -> assertThat(payment.getStatus()).isEqualTo(PaymentStatus.SUCCEED)
        );
    }

    @Test
    void testCompleteAfterCancel() {
        payment.complete();
        payment.cancel(BigDecimal.valueOf(30_000));

        assertThatThrownBy(() -> payment.complete())
                .isInstanceOf(PaymentException.class).message().isEqualTo("결제 상태를 변경 할 수 없습니다.");
    }

    @Test
    void testCompleteAfterFail() {
        payment.fail();

        assertThatThrownBy(() -> payment.complete())
                .isInstanceOf(PaymentException.class).message().isEqualTo("결제 상태를 변경 할 수 없습니다.");
    }

    @Test
    void testFail() {
        payment.fail();

        assertAll(
                () -> assertThat(payment.isSuccess()).isFalse(),
                () -> assertThat(payment.getStatus()).isEqualTo(PaymentStatus.FAILED)
        );
    }

    @Test
    void testCompleteAfterSuccess() {
        payment.complete();

        assertThatThrownBy(() -> payment.fail())
                .isInstanceOf(PaymentException.class).message().isEqualTo("결제 상태를 변경 할 수 없습니다.");
    }

    @Test
    void testPartialCancel() {
        payment.complete();
        payment.cancel(BigDecimal.valueOf(20_000));
        assertAll(
                () -> assertThat(payment.getStatus()).isEqualTo(PaymentStatus.PARTIALLY_REFUND),
                () -> assertThat(payment.getRemainingAmount()).isEqualTo(BigDecimal.valueOf(10_000))
        );
    }

    @Test
    void testCancel() {
        payment.complete();
        payment.cancel(BigDecimal.valueOf(30_000));
        assertAll(
                () -> assertThat(payment.getStatus()).isEqualTo(PaymentStatus.REFUNDED),
                () -> assertThat(payment.getRemainingAmount()).isEqualTo(BigDecimal.ZERO)
        );
    }
}