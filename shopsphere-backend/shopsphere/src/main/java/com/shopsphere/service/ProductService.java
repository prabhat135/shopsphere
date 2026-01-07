package com.shopsphere.service;

import com.shopsphere.model.Products;
import com.shopsphere.model.StyleCategory;
import com.shopsphere.model.GenderCategory;

import java.util.List;

public interface ProductService {
    List<Products> getAllProducts();
    List<Products> getProductsByCategory(StyleCategory style, GenderCategory gender);
    Products getProductById(Long id);
}
