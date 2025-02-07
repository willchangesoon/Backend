package com.es3.es3backend.store.controller;

import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.config.exception.StoreException;
import com.es3.es3backend.seller.domain.Seller;
import com.es3.es3backend.store.dto.StoreDto;
import com.es3.es3backend.store.dto.request.StoreCreateForm;
import com.es3.es3backend.store.dto.request.StoreUpdateRequest;
import com.es3.es3backend.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
@Tag(name = "상점", description = "상점 API 입니다")
public class StoreController {
    private final StoreService storeService;

    @GetMapping("")
    @Operation(summary = "상점 불러오기", description = "유저(셀러)의 상점을 불러옵니다.")
    public ResponseEntity<StoreDto> getStore(@AuthenticationPrincipal Seller seller) {
        return ResponseEntity.status(HttpStatus.OK).body(storeService.getSellerStore(seller));
    }

    @PostMapping("")
    @Operation(summary = "상점 생성" , description = "상점 생성 API 입니다.")
    public ResponseEntity createStore(@AuthenticationPrincipal Seller seller, @RequestBody StoreCreateForm form) {
        if (seller == null) {
            throw new StoreException(ErrorCode.NOT_AUTHORIZED);
        }
        storeService.createStore(form, seller);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/{id}/update-address")
    @Operation(summary = "상점 주소 수정" , description = "상점 주소 정보 수정 API 입니다.")
    public void updateStoreAddress(@AuthenticationPrincipal Seller seller, @PathVariable long id, @RequestBody StoreUpdateRequest.Address address) {
        storeService.updateAddress(seller, id, address);
    }

    @PatchMapping("/{id}/update-description")
    @Operation(summary = "상점 설명 수정" , description = "상점 설명 수정 API 입니다.")
    public void updateStoreDescription(@AuthenticationPrincipal Seller seller, @PathVariable long id, @RequestBody StoreUpdateRequest.Description description) {
        storeService.updateDescription(seller, id, description);
    }

    @PatchMapping("/{id}/update-contact")
    @Operation(summary = "상점 연락처 수정" , description = "상점 연락처 수정 API 입니다.")
    public void updateStoreContact(@AuthenticationPrincipal Seller seller, @PathVariable long id, @RequestBody StoreUpdateRequest.ContactNumber contactNumber) {
        storeService.updateContactNumber(seller, id, contactNumber);
    }

    @PatchMapping("/{id}/update-logo-image")
    @Operation(summary = "상점 로고 이미지 수정" , description = "상점 로고 이미지 수정 API 입니다.")
    public void updateStoreLogoImg(@AuthenticationPrincipal Seller seller, @PathVariable long id, @RequestBody StoreUpdateRequest.LogoImg logoImg) {
        storeService.updateLogoImg(seller, id, logoImg);
    }
}
