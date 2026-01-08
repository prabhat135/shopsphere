package com.shopsphere.controller;

import com.shopsphere.dto.ProductDTO;
import com.shopsphere.model.Product;
import com.shopsphere.model.ProductCategory;
import com.shopsphere.model.Gender;
import com.shopsphere.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> dtos = products.stream()
            .map(productService::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(productService.convertToDTO(product));
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable ProductCategory category) {
        List<Product> products = productService.getProductsByCategory(category);
        List<ProductDTO> dtos = products.stream()
            .map(productService::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<ProductDTO>> getProductsByGender(@PathVariable Gender gender) {
        List<Product> products = productService.getProductsByGender(gender);
        List<ProductDTO> dtos = products.stream()
            .map(productService::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/featured")
    public ResponseEntity<List<ProductDTO>> getFeaturedProducts() {
        List<Product> products = productService.getFeaturedProducts();
        List<ProductDTO> dtos = products.stream()
            .map(productService::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/new")
    public ResponseEntity<List<ProductDTO>> getNewArrivals() {
        List<Product> products = productService.getNewArrivals();
        List<ProductDTO> dtos = products.stream()
            .map(productService::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/filter")
    public ResponseEntity<List<ProductDTO>> filterProducts(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        
        List<Product> products = productService.filterProducts(category, gender, minPrice, maxPrice);
        List<ProductDTO> dtos = products.stream()
            .map(productService::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}