package com.luv4code.ecommerce.product.controller;

import com.luv4code.ecommerce.product.dto.request.ProductPurchaseRequest;
import com.luv4code.ecommerce.product.dto.request.ProductRequest;
import com.luv4code.ecommerce.product.dto.response.ProductPurchaseResponse;
import com.luv4code.ecommerce.product.dto.response.ProductResponse;
import com.luv4code.ecommerce.product.service.ProductService;
import jakarta.servlet.ServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(
            @RequestBody List<ProductPurchaseRequest> request,
            ServletResponse servletResponse) {
        return ResponseEntity.ok(productService.purchaseProducts(request));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable("product-id") Integer productId){
        return ResponseEntity.ok(productService.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

}
