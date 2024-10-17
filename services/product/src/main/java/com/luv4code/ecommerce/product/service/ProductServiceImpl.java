package com.luv4code.ecommerce.product.service;

import com.luv4code.ecommerce.product.dto.request.ProductPurchaseRequest;
import com.luv4code.ecommerce.product.dto.request.ProductRequest;
import com.luv4code.ecommerce.product.dto.response.ProductPurchaseResponse;
import com.luv4code.ecommerce.product.dto.response.ProductResponse;
import com.luv4code.ecommerce.product.exception.ProductPurchaseException;
import com.luv4code.ecommerce.product.mapper.ProductMapper;
import com.luv4code.ecommerce.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public Integer createProduct(ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    @Override
    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product Not found with the ID:: " + productId));
    }

    @Override
    public List<ProductResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toProductResponse)
                .toList();
    }

    @Override
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = repository.findAllByIdInOrderById(productIds);

        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }

        var storedRequest = request.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);

            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: "+productRequest.productId());
            }

            var newAvailabilityQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailabilityQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }

        return purchasedProducts;
    }
}
