package com.mr.deanshop.service;

import com.mr.deanshop.dto.CategoryDto;
import com.mr.deanshop.dto.CategoryTypeDto;
import com.mr.deanshop.entity.Category;
import com.mr.deanshop.entity.CategoryType;
import com.mr.deanshop.exceptions.ResourceNotFoundEx;
import com.mr.deanshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategory(UUID categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        return category.orElse(null);
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category createCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        return categoryRepository.save(category);
    }

    private Category mapToEntity(CategoryDto categoryDto) {
        Category category = Category.builder()
                .name(categoryDto.getName())
                .code(categoryDto.getCode())
                .description(categoryDto.getDescription())
                .build();

        List<CategoryTypeDto> categoryTypeList = categoryDto.getCategoryTypes();
        System.out.println(categoryTypeList);

        if(categoryTypeList != null) {
            List<CategoryType> categoryTypes = mapToCategoryTypeList(categoryTypeList, category);
            category.setCategoryTypes(categoryTypes);
        }

        return category;
    }

    //convert list of dto to list of entity
    private List<CategoryType> mapToCategoryTypeList(List<CategoryTypeDto> categoryTypeList, Category category) {

        return categoryTypeList.stream().map(categoryTypeDto -> {
            CategoryType categoryType = new CategoryType();
            categoryType.setName(categoryTypeDto.getName());
            categoryType.setCode(categoryTypeDto.getCode());
            categoryType.setDescription(categoryTypeDto.getDescription());
            categoryType.setCategory(category);
            return categoryType;
        }).toList();
    }

    public Category updateCategory(CategoryDto categoryDto, UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundEx("Not found category with id "+ categoryId));
        if(categoryDto.getName() != null) {
            category.setName(categoryDto.getName());
        }
        if(categoryDto.getCode() != null) {
            category.setCode(categoryDto.getCode());
        }
        if(categoryDto.getDescription() != null) {
            category.setDescription(categoryDto.getDescription());
        }

        List<CategoryType> existing = category.getCategoryTypes();
        List<CategoryType> list = new ArrayList<>();

        if(categoryDto.getCategoryTypes() != null) {
            categoryDto.getCategoryTypes().forEach(categoryTypeDto -> {
                if(categoryTypeDto.getId() != null) {
                    Optional<CategoryType> categoryType = existing.stream().filter(t -> t.getId().equals(categoryTypeDto.getId())).findFirst();
                    if(categoryType.isPresent()) {
                        CategoryType categoryType1 = categoryType.get();
                        categoryType1.setName(categoryTypeDto.getName());
                        categoryType1.setCode(categoryTypeDto.getCode());
                        categoryType1.setDescription(categoryTypeDto.getDescription());
                        list.add(categoryType1);
                    }
                }else{
                    CategoryType categoryType = new CategoryType();
                    categoryType.setName(categoryTypeDto.getName());
                    categoryType.setCode(categoryTypeDto.getCode());
                    categoryType.setDescription(categoryTypeDto.getDescription());
                    categoryType.setCategory(category);
                    list.add(categoryType);
                }
            });
        }

        category.setCategoryTypes(list);
        return categoryRepository.save(category);
    }

    public void deleteById(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
