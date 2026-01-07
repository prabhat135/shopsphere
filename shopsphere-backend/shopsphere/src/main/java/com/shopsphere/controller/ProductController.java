package com.shopsphere.controller;

import com.shopsphere.model.*;
import com.shopsphere.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Products> getProducts(
        @RequestParam(required = false) StyleCategory style,
        @RequestParam(required = false) GenderCategory gender
    ) {
        if (style != null && gender != null) {
            return productService.getProductsByCategory(style, gender);
        }
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Products getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }
}
