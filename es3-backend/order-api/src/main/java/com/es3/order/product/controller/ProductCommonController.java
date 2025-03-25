package com.es3.order.product.controller;

import com.es3.order.common.pagination.CursorPageResponse;
import com.es3.order.product.dto.ProductResponse;
import com.es3.order.product.service.ProductCommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common/products")
public class ProductCommonController {
    private final ProductCommonService productCommonService;

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productCommonService.getAllProducts());
    }

    @GetMapping("")
    public ResponseEntity<CursorPageResponse<ProductResponse>> getProducts(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        CursorPageResponse<ProductResponse> response = productCommonService.getProductsByCursor(cursor, size);
        return ResponseEntity.ok(response);
    }
}
