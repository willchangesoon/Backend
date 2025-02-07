package com.es3.es3backend.banner.controller;

import com.es3.es3backend.banner.dto.BannerDto;
import com.es3.es3backend.banner.dto.request.BannerRequestForm;
import com.es3.es3backend.banner.dto.request.BannerUpdateForm;
import com.es3.es3backend.banner.service.BannerService;
import com.es3.es3backend.seller.domain.Seller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/sellers/banners")
@Tag(name = "샐러 배너", description = "샐러의 배너 api 입니다.")
public class SellerBannerController {
    private final BannerService bannerService;

    @PostMapping("")
    @Operation(summary = "배너 생성", description = "셀러 회원 조회 후 셀러의 store 의 배너로 생성됩니다.")
    public ResponseEntity createBanner(@AuthenticationPrincipal Seller seller, @RequestBody BannerRequestForm bannerRequestForm) {
        bannerService.createBanner(bannerRequestForm, seller);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/imageLink")
    @Operation(summary = "배너 이미지 업데이트", description = "배너 이미지 url을 업데이트 합니다.")
    public ResponseEntity<BannerDto> updateImageLink(@AuthenticationPrincipal Seller seller, @PathVariable long id, @RequestBody BannerUpdateForm.ImageLink imageLink) {
        bannerService.updateImageLink(seller, id, imageLink);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PatchMapping("/{id}/start-dt")
    @Operation(summary = "배너 시작일 업데이트", description = "배너 시작일을 업데이트 합니다.")
    public ResponseEntity<BannerDto> updateStartDt(@AuthenticationPrincipal Seller seller, @PathVariable long id, @RequestBody BannerUpdateForm.StartDt startDt) {
        bannerService.updateStartDt(seller, id, startDt);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PatchMapping("/{id}/end-dt")
    @Operation(summary = "배너 종료일 업데이트", description = "배너 종료일을 업데이트 합니다.")
    public ResponseEntity<BannerDto> updateEndDt(@AuthenticationPrincipal Seller seller, @PathVariable long id, @RequestBody BannerUpdateForm.EndDt endDt) {
        bannerService.updateEndDt(seller, id, endDt);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "배너 삭제", description = "배너를 삭제합니다.")
    public ResponseEntity deleteBanner(@AuthenticationPrincipal Seller seller, @PathVariable long id) {
        bannerService.delete(seller, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
