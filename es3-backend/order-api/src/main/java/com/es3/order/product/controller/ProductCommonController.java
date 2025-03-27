package com.es3.order.product.controller;

import com.es3.order.common.pagination.CursorPageResponse;
import com.es3.order.product.dto.response.ProductDetailResponse;
import com.es3.order.product.dto.response.ProductResponse;
import com.es3.order.product.service.ProductCommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProductDetail(@PathVariable("id") Long id)   {
        return ResponseEntity.ok(productCommonService.getProductDetail(id));
    }
}
