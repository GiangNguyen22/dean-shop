package com.mr.deanshop.service;

import com.mr.deanshop.dto.ProductDto;
import com.mr.deanshop.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product addProduct(ProductDto product);
    List<ProductDto> getAllProducts(UUID categoryId, UUID categoryTypeId);

    ProductDto getProductById(UUID id);

    Product updateProduct(ProductDto productDto);

    ProductDto getProductBySlug(String slug);

    Product fetchProductById(UUID productId);
}
