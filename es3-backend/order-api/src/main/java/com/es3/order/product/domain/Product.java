package com.es3.order.product.domain;

import com.es3.order.common.entity.BaseEntity;
import com.es3.order.product.dto.ProductCreateForm;
import com.es3.order.product.dto.ProductOptionGroupForm;
import com.es3.order.product.dto.SKUForm;
import com.es3.order.store.domain.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.*;

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

    @Column(name = "discount")
    private int discount;

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
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private List<ProductOption> productOptions = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOptionGroup> optionGroups = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductSKU> productSKUs = new ArrayList<>();

    public static Product createProduct(ProductCreateForm form, Store store) {
        //todo discount 정리
        return new Product(null, store, form.title(), form.price(), 0, form.visibility(), form.deliveryType(), form.categoryId(), form.mainImage(),
                form.additionalImages(), form.description(), new ArrayList<>(), new ArrayList<>());
    }

//    public void addProductOptions(List<ProductOptionForm> optionForms) {
//        if (optionForms == null || optionForms.isEmpty())  return;
//        List<ProductOption> options = optionForms.stream()
//                .map(form -> ProductOption.createOption(this, form))
//                .toList();
//
//        this.productOptions.addAll(options);
//    }

    public void applyOptionsAndSKUs(List<ProductOptionGroupForm> groupForms, List<SKUForm> skuForms) {
        // 1. 옵션 그룹 및 옵션 등록
        Map<String, ProductOption> valueToOptionMap = new HashMap<>();
        for (ProductOptionGroupForm groupForm : groupForms) {
            ProductOptionGroup group = new ProductOptionGroup(null, groupForm.name(), this, new ArrayList<>());
            for (String value : groupForm.values()) {
                ProductOption option = new ProductOption(null, value, group);
                group.getOptions().add(option);
                valueToOptionMap.put(value, option);
            }
            this.optionGroups.add(group);
        }

        // 2. SKU 조합 등록
        for (SKUForm skuForm : skuForms) {
            List<ProductOption> matchedOptions = skuForm.optionValues().stream()
                    .map(valueToOptionMap::get)
                    .filter(Objects::nonNull)
                    .toList();

            if (matchedOptions.size() != skuForm.optionValues().size()) {
                throw new IllegalArgumentException("일치하는 옵션이 없습니다");
            }

            ProductSKU sku = new ProductSKU(null, this, matchedOptions, skuForm.quantity(), skuForm.additionalPrice());
            this.productSKUs.add(sku);
        }
    }

}
