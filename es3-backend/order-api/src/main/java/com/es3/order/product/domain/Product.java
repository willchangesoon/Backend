package com.es3.order.product.domain;

import com.es3.order.common.entity.BaseEntity;
import com.es3.order.product.dto.ProductCreateForm;
import com.es3.order.product.dto.ProductOptionForm;
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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "visibility")
    private boolean visibility;

    @Column(name = "deliveryType")
    private String deliveryType;

    @Column(name = "category_id")
    private Long category;

    @Column(name = "main_img")
    private String mainImg;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> additionalImages = new ArrayList<>();

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description; // Rich Text 저장 (HTML 또는 JSON)

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOption> productOptions = new ArrayList<>();


    public static Product createProduct(ProductCreateForm form, Store store) {
        return new Product(null, store, form.title(), form.price(), form.visibility(), form.deliveryType(), form.categoryId(), form.mainImage(),
                form.additionalImages(), form.description(), new ArrayList<>());
    }

    public void addProductOptions(List<ProductOptionForm> optionForms) {
        if (optionForms == null || optionForms.isEmpty())  return;
        List<ProductOption> options = optionForms.stream()
                .map(form -> ProductOption.createOption(this, form))
                .toList();

        this.productOptions.addAll(options);
    }
}
