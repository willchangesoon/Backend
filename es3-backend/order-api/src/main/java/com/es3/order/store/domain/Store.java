package com.es3.order.store.domain;

import com.es3.order.banner.domain.Banner;
import com.es3.order.common.entity.BaseEntity;
import com.es3.order.order.domain.OrderStore;
import com.es3.order.store.StoreStatus;
import com.es3.order.store.dto.request.StoreCreateForm;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_stores")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long sellerId;

    @OneToMany(mappedBy = "store")
    private List<Banner> banners = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<OrderStore> orderStores = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "logo_img", nullable = false)
    private String logoImg;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "contact_number", nullable = false)
    private String contactNumber;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "store_status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StoreStatus status;

    @Builder
    public Store(Long sellerId, String name, String logoImg, String description, String contactNumber, String address, StoreStatus status) {
        this.sellerId = sellerId;
        this.name = name;
        this.logoImg = logoImg;
        this.description = description;
        this.contactNumber = contactNumber;
        this.address = address;
        this.status = StoreStatus.ACTIVATE;
    }

    public static Store createStore(StoreCreateForm form, String sellerId) {
        return Store.builder()
                .name(form.name())
                .logoImg(form.logoImg())
                .address(form.address())
                .description(form.description())
                .contactNumber(form.contactNumber())
                .sellerId(Long.valueOf(sellerId))
                .build();
    }

    public void updateLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void addBanners(Banner banner) {
        this.banners.add(banner);
    }
}
