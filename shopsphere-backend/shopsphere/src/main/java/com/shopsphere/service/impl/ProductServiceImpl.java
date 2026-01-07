package com.shopsphere.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopsphere.model.GenderCategory;
import com.shopsphere.model.Products;
import com.shopsphere.model.StyleCategory;
import com.shopsphere.repository.ProductRepository;
import com.shopsphere.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Products> getProductsByCategory(
            StyleCategory style,
            GenderCategory gender) {

        return productRepository
                .findByStyleCategoryAndGenderCategory(style, gender);
    }

    @Override
    public Products getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
