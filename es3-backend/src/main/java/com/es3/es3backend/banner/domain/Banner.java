package com.es3.es3backend.banner.domain;

import com.es3.es3backend.config.exception.BannerException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.store.domain.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_banners")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "image_link", nullable = false)
    private String imageLink;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "start_dt")
    private LocalDateTime startDt;

    @Column(name = "end_dt")
    private LocalDateTime endDt;

    @Builder
    public Banner(String imageLink, Store store, LocalDateTime startDt, LocalDateTime endDt) {
        this.imageLink = imageLink;
        this.store = store;
        this.startDt = startDt;
        this.endDt = endDt;
    }

    public void updateImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void updateStartDt(LocalDateTime startDt) {
        if (this.startDt.isAfter(this.endDt)) {
            throw new BannerException(ErrorCode.DATE_VALIDATION);
        }
        this.startDt = startDt;
    }

    public void updateEndDt(LocalDateTime endDt) {
        if (this.endDt.isBefore(this.startDt)) {
            throw new BannerException(ErrorCode.DATE_VALIDATION);
        }
        this.endDt = endDt;
    }
}
