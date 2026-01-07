package com.shopsphere.repository;

import com.shopsphere.model.Products;
import com.shopsphere.model.StyleCategory;
import com.shopsphere.model.GenderCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {

    List<Products> findByStyleCategoryAndGenderCategory(
        StyleCategory styleCategory,
        GenderCategory genderCategory
    );
}
