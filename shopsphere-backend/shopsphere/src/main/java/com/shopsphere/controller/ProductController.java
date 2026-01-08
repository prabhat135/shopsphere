package com.shopsphere.controller;

import com.shopsphere.dto.ApiResponse;
import com.shopsphere.dto.FilterOptions;
import com.shopsphere.dto.ProductDTO;
import com.shopsphere.model.Product;
import com.shopsphere.model.ProductCategory;
import com.shopsphere.model.Gender;
import com.shopsphere.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ============================
    // GET ALL PRODUCTS
    // ============================
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {

        List<ProductDTO> dtos = productService.getAllProducts()
                .stream()
                .map(productService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    // ============================
    // GET PRODUCT BY ID (SAFE)
    // ============================
    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long id) {

        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success(
                productService.convertToDTO(product)
        ));
    }

    // ============================
    // BY CATEGORY
    // ============================
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductsByCategory(
            @PathVariable ProductCategory category) {

        List<ProductDTO> dtos = productService.getProductsByCategory(category)
                .stream()
                .map(productService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    // ============================
    // BY GENDER
    // ============================
    @GetMapping("/gender/{gender}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductsByGender(
            @PathVariable Gender gender) {

        List<ProductDTO> dtos = productService.getProductsByGender(gender)
                .stream()
                .map(productService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    // ============================
    // FEATURED
    // ============================
    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getFeaturedProducts() {

        List<ProductDTO> dtos = productService.getFeaturedProducts()
                .stream()
                .map(productService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    // ============================
    // NEW ARRIVALS
    // ============================
    @GetMapping("/new")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getNewArrivals() {

        List<ProductDTO> dtos = productService.getNewArrivals()
                .stream()
                .map(productService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    // ============================
    // FILTER (POST ONLY)
    // ============================
    @PostMapping("/filter")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> filterProducts(
            @RequestBody FilterOptions filterOptions) {

        Double minPrice = filterOptions.getPriceRange() != null
                ? filterOptions.getPriceRange().getMin()
                : null;

        Double maxPrice = filterOptions.getPriceRange() != null
                ? filterOptions.getPriceRange().getMax()
                : null;

        List<ProductDTO> dtos = productService
                .filterProductsAdvanced(
                        filterOptions.getCategories(),
                        filterOptions.getGenders(),
                        minPrice,
                        maxPrice,
                        filterOptions.getSizes(),
                        filterOptions.getColors(),
                        filterOptions.getSortBy()
                )
                .stream()
                .map(productService::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(dtos));
    }


    // ============================
    // METADATA
    // ============================
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<String>>> getAllCategories() {

        List<String> categories = Arrays.stream(ProductCategory.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    @GetMapping("/genders")
    public ResponseEntity<ApiResponse<List<String>>> getAllGenders() {

        List<String> genders = Arrays.stream(Gender.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(genders));
    }

    @GetMapping("/sizes")
    public ResponseEntity<ApiResponse<List<String>>> getAllSizes() {

        return ResponseEntity.ok(
                ApiResponse.success(List.of("XS", "S", "M", "L", "XL", "XXL"))
        );
    }

    @GetMapping("/colors")
    public ResponseEntity<ApiResponse<List<String>>> getAllColors() {

        return ResponseEntity.ok(
                ApiResponse.success(List.of("BLACK", "WHITE", "RED", "BLUE", "GREEN", "YELLOW"))
        );
    }
}
