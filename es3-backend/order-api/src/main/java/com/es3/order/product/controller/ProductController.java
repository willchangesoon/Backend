package com.es3.order.product.controller;

import com.es3.order.product.dto.ProductCreateForm;
import com.es3.order.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public void createProduct(@RequestHeader("X-User-Id") String userId, @RequestBody ProductCreateForm form){
        productService.createProduct(userId, form);
    }
}
