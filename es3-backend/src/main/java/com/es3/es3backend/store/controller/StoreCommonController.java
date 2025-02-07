package com.es3.es3backend.store.controller;

import com.es3.es3backend.seller.domain.Seller;
import com.es3.es3backend.store.dto.StoreDto;
import com.es3.es3backend.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common/store")
@Tag(name = "상점 공통", description = "토큰/인가 없이 조회 가능한 상점 API 입니다.")
public class StoreCommonController {

    private final StoreService storeService;

    @GetMapping("/{id}")
    @PermitAll
    @Operation(summary = "상점 정보" , description = "상점의 id 로 상점의 정보를 주는 API 입니다.")
    public ResponseEntity<StoreDto> getStoreById(@AuthenticationPrincipal Seller seller, @PathVariable long id) {
        return ResponseEntity.status(200).body(storeService.getStoreById(seller, id));
    }

    @GetMapping("/activate-list")
    @PermitAll
    @Operation(summary = "상점 리스트" , description = "상태가 활성화된 상점들의 리스트를 주는 API 입니다.")
    public ResponseEntity<List<StoreDto>> getAllActivatedStores() {
        return ResponseEntity.status(200).body(storeService.getAvailableStores(null, 5));
    }
}
