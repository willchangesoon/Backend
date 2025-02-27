package com.es3.order.banner.service;

import com.es3.order.banner.domain.Banner;
import com.es3.order.banner.domain.BannerRepository;
import com.es3.order.banner.dto.request.BannerRequestForm;
import com.es3.order.banner.dto.request.BannerUpdateForm;
import com.es3.order.config.exception.BannerException;
import com.es3.order.config.exception.ErrorCode;
import com.es3.order.config.exception.StoreException;
import com.es3.order.store.domain.Store;
import com.es3.order.store.domain.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;
    private final StoreRepository storeRepository;

    public void createBanner(BannerRequestForm form, Long sellerId) {
        validateBannerPeriod(form.startDt(), form.endDt());
        Store store = getStore(sellerId);
        Banner banner = bannerRepository.save(Banner.builder()
                .imageLink(form.imageLink())
                .store(store)
                .startDt(form.startDt())
                .endDt(form.endDt())
                .build());
        store.addBanners(banner);
    }

    private void validateBannerPeriod(LocalDateTime startDt, LocalDateTime endDt) {
        if (startDt.isAfter(endDt) || startDt.isEqual(endDt)) {
            throw new BannerException(ErrorCode.DATE_VALIDATION);
        }
    }

    public void updateImageLink(Long sellerId, Long bannerId, BannerUpdateForm.ImageLink imageLink) {
        Banner banner = getBannerFromSellerShop(bannerId, sellerId);
        banner.updateImageLink(imageLink.imageLink());
    }

    public void updateStartDt(Long sellerId, Long bannerId, BannerUpdateForm.StartDt startDt) {
        Banner banner = getBannerFromSellerShop(bannerId, sellerId);
        banner.updateStartDt(startDt.startDt());
    }

    public void updateEndDt(Long sellerId, Long bannerId, BannerUpdateForm.EndDt endDt) {
        Banner banner = getBannerFromSellerShop(bannerId, sellerId);
        banner.updateEndDt(endDt.endDt());
    }

    public void delete(Long sellerId, Long bannerId) {
        bannerRepository.delete(getBannerFromSellerShop(bannerId, sellerId));
    }

    private Banner getBannerFromSellerShop(Long bannerId, Long sellerId) {
        Store store = getStore(sellerId);
        Banner banner = bannerRepository.findById(bannerId).orElseThrow(() -> new BannerException(ErrorCode.BANNER_NOT_FOUND));

        if (!banner.getStore().equals(store)) {
            throw new BannerException(ErrorCode.BANNER_NOT_FOUND);
        }

        return banner;
    }

    private Store getStore(Long sellerId) {
        return storeRepository.findBySellerId(sellerId)
                .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
    }
}
