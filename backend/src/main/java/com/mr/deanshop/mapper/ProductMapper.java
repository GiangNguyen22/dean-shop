package com.mr.deanshop.mapper;

import com.mr.deanshop.dto.ProductDto;
import com.mr.deanshop.dto.ProductResourceDto;
import com.mr.deanshop.dto.ProductVariantDto;
import com.mr.deanshop.entity.*;
import com.mr.deanshop.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    public Product mapToProductEntity(ProductDto productDto) {
        Product product = new Product();
        if(productDto.getId() != null) {
            product.setId(productDto.getId());
        }
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setBrand(productDto.getBrand());
        product.setNewArrival(productDto.isNewArrival());
        product.setPrice(productDto.getPrice());
        product.setRating(productDto.getRating());
        product.setSlug(productDto.getSlug());

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

    public List<Resources> mapToProductResources(List<ProductResourceDto> productResources, Product product) {
        return productResources.stream().map(productResouceDto -> {
            Resources resource = new Resources();
            if(null != productResouceDto.getId()) {
                resource.setId(productResouceDto.getId());
            }
            resource.setName(productResouceDto.getName());
            resource.setUrl(productResouceDto.getUrl());
            resource.setType(productResouceDto.getType());
            resource.setPrimary(productResouceDto.isPrimary());
            resource.setProduct(product);
            return resource;
        }).collect(Collectors.toList());
    }

    public List<ProductVariant> mapToProductVariants(List<ProductVariantDto> variants, Product product) {
        return variants.stream().map(variantDto -> {
            ProductVariant productVariant = new ProductVariant();
            if(null != variantDto.getId()) {
                productVariant.setId(variantDto.getId());
            }
            productVariant.setColor(variantDto.getColor());
            productVariant.setSize(variantDto.getSize());
            productVariant.setStockQuantity(variantDto.getStockQuantity());
            productVariant.setProduct(product);
            return productVariant;
        }).collect(Collectors.toList());
    }

    public ProductDto mapToProductDto(Product product) {
        ProductDto productDto =  modelMapper.map(product, ProductDto.class);
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setCategoryTypeId(product.getCategoryType().getId());
        productDto.setThumbnail(getProductThumbnail(product.getResources()));
        productDto.setProductResources(mapProductResourceListDto(product.getResources()));
        return productDto;
    }

    private String getProductThumbnail(List<Resources> resources) {
        return resources.stream().filter(Resources::isPrimary).findFirst().map(Resources::getUrl).orElse(null);
    }

    public List<ProductVariantDto> mapProductVariantListDto(List<ProductVariant> productVariants) {
        return productVariants.stream().map(this::mapToProductVariantDto).toList();
    }

    private ProductVariantDto mapToProductVariantDto(ProductVariant productVariant) {
       return modelMapper.map(productVariant, ProductVariantDto.class);
    }

    public List<ProductResourceDto> mapProductResourceListDto(List<Resources> resources) {
        return resources.stream().map(this::mapToResourceDto).toList();
    }

    private ProductResourceDto mapToResourceDto(Resources resources) {
        return modelMapper.map(resources, ProductResourceDto.class);
    }

    public List<ProductDto> getProductDtos(List<Product> products) {
        return products.stream().map(this::mapToProductDto).toList();
    }
}
