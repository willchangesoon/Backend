package com.es3.order.store.controller;

import com.es3.order.store.dto.StoreDto;
import com.es3.order.store.dto.request.StoreCreateForm;
import com.es3.order.store.dto.request.StoreUpdateRequest;
import com.es3.order.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
@Tag(name = "상점", description = "상점 API 입니다")
public class StoreController {
    private final StoreService storeService;

    @GetMapping("")
    @Operation(summary = "상점 불러오기", description = "유저(셀러)의 상점을 불러옵니다.")
    public ResponseEntity<StoreDto> getStore(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(storeService.getSellerStore(userId));
    }

    @PostMapping("")
    @Operation(summary = "상점 생성" , description = "상점 생성 API 입니다.")
    public ResponseEntity createStore(@RequestHeader("X-User-Id") String userId, @RequestBody StoreCreateForm form) {
        storeService.createStore(form, userId);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/{id}/update-description")
    @Operation(summary = "상점 설명 수정" , description = "상점 설명 수정 API 입니다.")
    public void updateStoreDescription(@RequestHeader("X-User-Id") String userId, @PathVariable long id, @RequestBody StoreUpdateRequest.Description description) {
        storeService.updateDescription(userId, id, description);
    }

    @PatchMapping("/{id}/update-contact")
    @Operation(summary = "상점 연락처 수정" , description = "상점 연락처 수정 API 입니다.")
    public void updateStoreContact(@RequestHeader("X-User-Id") String userId, @PathVariable long id, @RequestBody StoreUpdateRequest.ContactNumber contactNumber) {
        storeService.updateContactNumber(userId, id, contactNumber);
    }

    @PatchMapping("/{id}/update-logo-image")
    @Operation(summary = "상점 로고 이미지 수정" , description = "상점 로고 이미지 수정 API 입니다.")
    public void updateStoreLogoImg(@RequestHeader("X-User-Id") String userId, @PathVariable long id, @RequestBody StoreUpdateRequest.LogoImg logoImg) {
        storeService.updateLogoImg(userId, id, logoImg);
    }
}
