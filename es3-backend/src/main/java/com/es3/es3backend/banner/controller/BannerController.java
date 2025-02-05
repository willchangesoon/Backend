package com.es3.es3backend.banner.controller;

import com.es3.es3backend.banner.dto.BannerDto;
import com.es3.es3backend.banner.dto.request.BannerRequestForm;
import com.es3.es3backend.banner.dto.request.BannerUpdateForm;
import com.es3.es3backend.banner.service.BannerService;
import com.es3.es3backend.seller.domain.Seller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/banners")
public class BannerController {
    private final BannerService bannerService;

    @PostMapping("")
    public ResponseEntity createBanner(@AuthenticationPrincipal Seller seller, @RequestBody BannerRequestForm bannerRequestForm) {
        bannerService.createBanner(bannerRequestForm, seller);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/imageLink")
    public ResponseEntity<BannerDto> updateImageLink(@AuthenticationPrincipal Seller seller, @PathVariable long id, @RequestBody BannerUpdateForm.ImageLink imageLink) {
        bannerService.updateImageLink(seller, id, imageLink);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PatchMapping("/{id}/start-dt")
    public ResponseEntity<BannerDto> updateStartDt(@AuthenticationPrincipal Seller seller, @PathVariable long id, @RequestBody BannerUpdateForm.StartDt startDt) {
        bannerService.updateStartDt(seller, id, startDt);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PatchMapping("/{id}/end-dt")
    public ResponseEntity<BannerDto> updateEndDt(@AuthenticationPrincipal Seller seller, @PathVariable long id, @RequestBody BannerUpdateForm.EndDt endDt) {
        bannerService.updateEndDt(seller, id, endDt);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBanner(@AuthenticationPrincipal Seller seller, @PathVariable long id) {
        bannerService.delete(seller, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
