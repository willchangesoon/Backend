package com.es3.order.banner.controller;

import com.es3.order.banner.dto.BannerDto;
import com.es3.order.banner.dto.request.BannerRequestForm;
import com.es3.order.banner.dto.request.BannerUpdateForm;
import com.es3.order.banner.service.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity createBanner(@RequestHeader("X-User-Id") String userId, @RequestBody BannerRequestForm bannerRequestForm) {
        bannerService.createBanner(bannerRequestForm, Long.valueOf(userId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/imageLink")
    @Operation(summary = "배너 이미지 업데이트", description = "배너 이미지 url을 업데이트 합니다.")
    public ResponseEntity<BannerDto> updateImageLink(@RequestHeader("X-User-Id") String userId, @PathVariable long id, @RequestBody BannerUpdateForm.ImageLink imageLink) {
        bannerService.updateImageLink(Long.valueOf(userId), id, imageLink);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PatchMapping("/{id}/start-dt")
    @Operation(summary = "배너 시작일 업데이트", description = "배너 시작일을 업데이트 합니다.")
    public ResponseEntity<BannerDto> updateStartDt(@RequestHeader("X-User-Id") String userId, @PathVariable long id, @RequestBody BannerUpdateForm.StartDt startDt) {
        bannerService.updateStartDt(Long.valueOf(userId), id, startDt);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PatchMapping("/{id}/end-dt")
    @Operation(summary = "배너 종료일 업데이트", description = "배너 종료일을 업데이트 합니다.")
    public ResponseEntity<BannerDto> updateEndDt(@RequestHeader("X-User-Id") String userId, @PathVariable long id, @RequestBody BannerUpdateForm.EndDt endDt) {
        bannerService.updateEndDt(Long.valueOf(userId), id, endDt);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "배너 삭제", description = "배너를 삭제합니다.")
    public ResponseEntity deleteBanner(@RequestHeader("X-User-Id") String userId, @PathVariable long id) {
        bannerService.delete(Long.valueOf(userId), id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
