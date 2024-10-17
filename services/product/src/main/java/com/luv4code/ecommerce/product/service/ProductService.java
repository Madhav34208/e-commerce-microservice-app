package com.luv4code.ecommerce.product.service;

import com.luv4code.ecommerce.product.dto.request.ProductPurchaseRequest;
import com.luv4code.ecommerce.product.dto.request.ProductRequest;
import com.luv4code.ecommerce.product.dto.response.ProductPurchaseResponse;
import com.luv4code.ecommerce.product.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    Integer createProduct( ProductRequest request);

    List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request);

    ProductResponse findById(Integer productId);

    List<ProductResponse> findAll();
}
