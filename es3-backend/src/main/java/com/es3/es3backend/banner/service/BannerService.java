package com.es3.es3backend.banner.service;

import com.es3.es3backend.banner.domain.Banner;
import com.es3.es3backend.banner.domain.BannerRepository;
import com.es3.es3backend.banner.dto.request.BannerRequestForm;
import com.es3.es3backend.banner.dto.request.BannerUpdateForm;
import com.es3.es3backend.config.exception.BannerException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.config.exception.StoreException;
import com.es3.es3backend.seller.domain.Seller;
import com.es3.es3backend.store.domain.Store;
import com.es3.es3backend.store.domain.StoreRepository;
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

    public void createBanner(BannerRequestForm form, Seller seller) {
        validateBannerPeriod(form.startDt(), form.endDt());
        Store store = getStore(seller.getStore());
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

    public void updateImageLink(Seller seller, Long bannerId, BannerUpdateForm.ImageLink imageLink) {
        Banner banner = getBannerFromSellerShop(bannerId, seller);
        banner.updateImageLink(imageLink.imageLink());
    }

    public void updateStartDt(Seller seller, Long bannerId, BannerUpdateForm.StartDt startDt) {
        Banner banner = getBannerFromSellerShop(bannerId, seller);
        banner.updateStartDt(startDt.startDt());
    }

    public void updateEndDt(Seller seller, Long bannerId, BannerUpdateForm.EndDt endDt) {
        Banner banner = getBannerFromSellerShop(bannerId, seller);
        banner.updateEndDt(endDt.endDt());
    }

    public void delete(Seller seller, Long bannerId) {
        bannerRepository.delete(getBannerFromSellerShop(bannerId, seller));
    }

    private Banner getBannerFromSellerShop(Long bannerId, Seller seller) {
        Store store = getStore(seller.getStore());
        Banner banner = bannerRepository.findById(bannerId).orElseThrow(() -> new BannerException(ErrorCode.BANNER_NOT_FOUND));

        if (!banner.getStore().equals(store)) {
            throw new BannerException(ErrorCode.BANNER_NOT_FOUND);
        }

        return banner;
    }

    private Store getStore(Store store) {
        return storeRepository.findById(store.getId())
                .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
    }
}
