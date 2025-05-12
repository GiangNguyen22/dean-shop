package com.mr.deanshop.service;

import com.mr.deanshop.dto.ProductDto;
import com.mr.deanshop.dto.ProductResourceDto;
import com.mr.deanshop.dto.ProductVariantDto;
import com.mr.deanshop.entity.*;
import com.mr.deanshop.repository.ProductRepository;
import com.mr.deanshop.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Product addProduct(ProductDto productDto) {
        Product product = mapToProductEntity(productDto);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts(UUID categoryId, UUID categoryTypeId) {
        Specification<Product> productSpecification = Specification.where(null);
        if(categoryId != null) {
            productSpecification = productSpecification.and(ProductSpecification.hasCategoryId(categoryId));
        }
        if(categoryTypeId != null) {
            productSpecification = productSpecification.and(ProductSpecification.hasCategoryTypeId(categoryTypeId));
        }

        List<Product> products = productRepository.findAll(productSpecification);
        
        return products;
    }

    private Product mapToProductEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setBrand(productDto.getBrand());
        product.setNewArrival(productDto.isNewArrival());
        product.setPrice(productDto.getPrice());
        product.setRating(productDto.getRating());

        Category category = categoryService.getCategory(productDto.getCategoryId());
        if(category != null) {
            product.setCategory(category);
            UUID categoryTypeId = productDto.getCategoryTypeId();

            CategoryType categoryType = category.getCategoryTypes().stream().filter(categoryType1 -> categoryType1.getId().equals(categoryTypeId)).findFirst().orElse(null);
            product.setCategoryType(categoryType);
        }
        if(productDto.getVariants() != null) {
            product.setVariants(mapToProductVariants(productDto.getVariants(), product));
        }

        if(productDto.getProductResources() != null) {
            product.setResources(mapToProductResources(productDto.getProductResources(), product));
        }
        return product;
    }

    private List<Resources> mapToProductResources(List<ProductResourceDto> productResources, Product product) {
        return productResources.stream().map(productResouceDto -> {
            Resources resource = new Resources();
            resource.setName(productResouceDto.getName());
            resource.setUrl(productResouceDto.getUrl());
            resource.setType(productResouceDto.getType());
            resource.setPrimary(productResouceDto.isPrimary());
            resource.setProduct(product);
            return resource;
        }).collect(Collectors.toList());
    }

    private List<ProductVariant> mapToProductVariants(List<ProductVariantDto> variants, Product product) {
        return variants.stream().map(variantDto -> {
            ProductVariant productVariant = new ProductVariant();
            productVariant.setColor(variantDto.getColor());
            productVariant.setSize(variantDto.getSize());
            productVariant.setStockQuantity(variantDto.getStockQuantity());
            productVariant.setProduct(product);
            return productVariant;
        }).collect(Collectors.toList());
    }
}
