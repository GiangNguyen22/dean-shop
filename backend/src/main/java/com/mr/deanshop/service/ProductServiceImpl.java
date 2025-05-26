package com.mr.deanshop.service;

import com.mr.deanshop.dto.ProductDto;
import com.mr.deanshop.dto.ProductResourceDto;
import com.mr.deanshop.dto.ProductVariantDto;
import com.mr.deanshop.entity.*;
import com.mr.deanshop.exceptions.ResourceNotFoundEx;
import com.mr.deanshop.mapper.ProductMapper;
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
    private ProductMapper productMapper;

    @Override
    public Product addProduct(ProductDto productDto) {
        Product product = productMapper.mapToProductEntity(productDto);
        return productRepository.save(product);
    }

    @Override
    public List<ProductDto> getAllProducts(UUID categoryId, UUID categoryTypeId) {
        Specification<Product> productSpecification = Specification.where(null);
        if(categoryId != null) {
            productSpecification = productSpecification.and(ProductSpecification.hasCategoryId(categoryId));
        }
        if(categoryTypeId != null) {
            productSpecification = productSpecification.and(ProductSpecification.hasCategoryTypeId(categoryTypeId));
        }

        List<Product> products = productRepository.findAll(productSpecification);
        return productMapper.getProductDtos(products);
    }

    @Override
    public ProductDto getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEx("Product not found"));
        ProductDto productDto = productMapper.mapToProductDto(product);
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setCategoryTypeId(product.getCategoryType().getId());
        productDto.setVariants(productMapper.mapProductVariantListDto(product.getVariants()));
        productDto.setProductResources(productMapper.mapProductResourceListDto(product.getResources()));
        return productDto;
    }

    @Override
    public ProductDto getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug);
        if(product == null) {
            throw new ResourceNotFoundEx("Product not found!");
        }
        ProductDto productDto = productMapper.mapToProductDto(product);
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setCategoryTypeId(product.getCategoryType().getId());
        productDto.setVariants(productMapper.mapProductVariantListDto(product.getVariants()));
        productDto.setProductResources(productMapper.mapProductResourceListDto(product.getResources()));
        return productDto;
    }

    @Override
    public Product updateProduct(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(()-> new ResourceNotFoundEx("Product not found"));
        return productRepository.save(productMapper.mapToProductEntity(productDto));
    }




}
