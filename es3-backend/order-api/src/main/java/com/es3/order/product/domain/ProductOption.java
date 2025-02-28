package com.es3.order.product.domain;

import com.es3.order.common.entity.BaseEntity;
import com.es3.order.product.dto.ProductOptionForm;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_product_options")
public class ProductOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "additional_price")
    private BigDecimal additionalPrice;

    public static ProductOption createOption(Product product, ProductOptionForm form) {
        return new ProductOption(null, product, form.name(), form.value(), form.quantity(), form.additionalPrice());
    }
}
