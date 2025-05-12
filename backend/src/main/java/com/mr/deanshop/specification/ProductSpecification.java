package com.mr.deanshop.specification;

import com.mr.deanshop.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

//class Specification de filter data
public class ProductSpecification {
    //tao dieu kien loc theo categoryId
    public static Specification<Product> hasCategoryId(UUID categoryId) {
        return (root, query, criterialBuilder) -> criterialBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> hasCategoryTypeId(UUID categoryTypeId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("categoryType").get("id"), categoryTypeId));
    }
}
