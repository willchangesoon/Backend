package com.es3.es3backend.store.domain;

import com.es3.es3backend.banner.domain.Banner;
import com.es3.es3backend.common.entity.BaseEntity;
import com.es3.es3backend.constants.StoreStatus;
import com.es3.es3backend.seller.domain.Seller;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_stores")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "store")
    private Seller seller;

    @OneToMany(mappedBy = "store")
    private List<Banner> banners = new ArrayList<>();

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
    public Store(String name, String logoImg, String description, String contactNumber, String address) {
        this.name = name;
        this.logoImg = logoImg;
        this.description = description;
        this.contactNumber = contactNumber;
        this.address = address;
        this.status = StoreStatus.ACTIVATE;
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
