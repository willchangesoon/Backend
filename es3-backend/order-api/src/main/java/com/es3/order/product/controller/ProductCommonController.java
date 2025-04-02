package com.es3.order.product.controller;

import com.es3.order.common.pagination.CursorPageResponse;
import com.es3.order.product.domain.Product;
import com.es3.order.product.domain.repo.ProductQueryRepository;
import com.es3.order.product.dto.ProductSearchCond;
import com.es3.order.product.dto.SortDirection;
import com.es3.order.product.dto.response.ProductDetailResponse;
import com.es3.order.product.dto.response.ProductOptionGroupResponse;
import com.es3.order.product.dto.response.ProductResponse;
import com.es3.order.product.dto.response.ProductSKUResponse;
import com.es3.order.product.service.ProductCommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common/products")
public class ProductCommonController {
    private final ProductCommonService productCommonService;
    private final ProductQueryRepository productQueryRepository;

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productCommonService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProductDetail(@PathVariable("id") Long id)   {
        return ResponseEntity.ok(productCommonService.getProductDetail(id));
    }

    @GetMapping("/{id}/options")
    public ResponseEntity<List<ProductOptionGroupResponse>> getProductOptions(@PathVariable("id") Long id) {
        return ResponseEntity.ok( productCommonService.getProductOptions(id));
    }

    @GetMapping("/{id}/skus")
    public ResponseEntity<List<ProductSKUResponse>> getProductSkus(@PathVariable("id") Long id) {
        return ResponseEntity.ok( productCommonService.getProductSkus(id));
    }

    @GetMapping("")
    public ResponseEntity<CursorPageResponse<ProductResponse>> searchProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) Boolean discounted,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "DESC") SortDirection sort,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(0, size);
        ProductSearchCond cond = ProductSearchCond.builder()
                .categoryId(categoryId)
                .storeId(storeId)
                .discounted(discounted)
                .cursorId(cursor)
                .sortDirection(sort)
                .build();

        List<Product> products = productQueryRepository.searchProducts(cond, pageable);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(CursorPageResponse.of(response, size));
    }

}
