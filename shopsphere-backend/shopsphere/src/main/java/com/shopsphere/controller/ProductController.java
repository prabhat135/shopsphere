// ProductController.java - Fixed version
package com.shopsphere.controller;

import com.shopsphere.dto.ApiResponse;
import com.shopsphere.dto.FilterRequestDTO;
import com.shopsphere.dto.ProductDTO;
import com.shopsphere.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    @Autowired
    private ProductService productService ;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {
        try {
            List<ProductDTO> products = productService.getAllProducts();
            return ResponseEntity.ok(ApiResponse.success("Products retrieved successfully", products));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Error retrieving products: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long id) {
        try {
            return productService.getProductById(id)
                    .map(product -> ResponseEntity.ok(ApiResponse.success("Product found", product)))
                    .orElse(ResponseEntity.ok(ApiResponse.error("Product not found with id: " + id)));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Error retrieving product: " + e.getMessage()));
        }
    }
    
    @GetMapping("/trending")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getTrendingProducts() {
        try {
            List<ProductDTO> products = productService.getTrendingProducts();
            return ResponseEntity.ok(ApiResponse.success("Trending products retrieved", products));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Error retrieving trending products: " + e.getMessage()));
        }
    }
    
    @GetMapping("/new-arrivals")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getNewArrivals() {
        try {
            List<ProductDTO> products = productService.getNewArrivals();
            return ResponseEntity.ok(ApiResponse.success("New arrivals retrieved", products));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Error retrieving new arrivals: " + e.getMessage()));
        }
    }
    
    @PostMapping("/filter")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> filterProducts(
            @RequestBody @Valid FilterRequestDTO filterRequest) {
        try {
            List<ProductDTO> products = productService.filterProducts(filterRequest);
            return ResponseEntity.ok(ApiResponse.success("Products filtered successfully", products));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Error filtering products: " + e.getMessage()));
        }
    }
    
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<String>>> getAllCategories() {
        try {
            List<String> categories = productService.getAllCategories();
            return ResponseEntity.ok(ApiResponse.success("Categories retrieved", categories));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Error retrieving categories: " + e.getMessage()));
        }
    }
    
    @GetMapping("/genders")
    public ResponseEntity<ApiResponse<List<String>>> getAllGenders() {
        try {
            List<String> genders = productService.getAllGenders();
            return ResponseEntity.ok(ApiResponse.success("Genders retrieved", genders));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Error retrieving genders: " + e.getMessage()));
        }
    }
    
    @GetMapping("/sizes")
    public ResponseEntity<ApiResponse<List<String>>> getAllSizes() {
        try {
            List<String> sizes = productService.getAllSizes();
            return ResponseEntity.ok(ApiResponse.success("Sizes retrieved", sizes));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Error retrieving sizes: " + e.getMessage()));
        }
    }
    
    @GetMapping("/colors")
    public ResponseEntity<ApiResponse<List<String>>> getAllColors() {
        try {
            List<String> colors = productService.getAllColors();
            return ResponseEntity.ok(ApiResponse.success("Colors retrieved", colors));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error("Error retrieving colors: " + e.getMessage()));
        }
    }
}