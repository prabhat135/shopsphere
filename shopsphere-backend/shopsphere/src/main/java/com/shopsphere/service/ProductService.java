// ProductService.java - Fixed version
package com.shopsphere.service;

import com.shopsphere.dto.FilterRequestDTO;
import com.shopsphere.dto.ProductDTO;
import com.shopsphere.model.Product;
import com.shopsphere.model.Category;
import com.shopsphere.model.Gender;
import com.shopsphere.repository.ProductRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private ProductRepository productRepository;
    
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    public List<ProductDTO> getTrendingProducts() {
        return productRepository.findByIsFeaturedTrue().stream()
                .map(this::convertToDTO)
                .limit(10)
                .collect(Collectors.toList());
    }
    
    public List<ProductDTO> getNewArrivals() {
        return productRepository.findByIsNewTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<ProductDTO> filterProducts(FilterRequestDTO filterRequest) {
        Specification<Product> spec = buildSpecification(filterRequest);
        Sort sort = buildSort(filterRequest.getSortBy());
        
        return productRepository.findAll(spec, sort).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<String> getAllCategories() {
        return productRepository.findAllDistinctCategories().stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }
    
    public List<String> getAllGenders() {
        return productRepository.findAllDistinctGenders().stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }
    
    public List<String> getAllSizes() {
        return productRepository.findAllDistinctSizes();
    }
    
    public List<String> getAllColors() {
        return productRepository.findAllDistinctColors();
    }
    
    private Specification<Product> buildSpecification(FilterRequestDTO filterRequest) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (filterRequest.getCategories() != null && !filterRequest.getCategories().isEmpty()) {
                List<Category> categories = filterRequest.getCategories().stream()
                        .map(Category::valueOf)
                        .collect(Collectors.toList());
                predicates.add(root.get("category").in(categories));
            }
            
            if (filterRequest.getGenders() != null && !filterRequest.getGenders().isEmpty()) {
                List<Gender> genders = filterRequest.getGenders().stream()
                        .map(Gender::valueOf)
                        .collect(Collectors.toList());
                predicates.add(root.get("gender").in(genders));
            }
            
            if (filterRequest.getPriceRange() != null) {
                // Fix: Use getMin() and getMax() methods
                double min = filterRequest.getPriceRange().getMin() != null ? 
                             filterRequest.getPriceRange().getMin() : 0.0;
                double max = filterRequest.getPriceRange().getMax() != null ? 
                             filterRequest.getPriceRange().getMax() : 10000.0;
                
                predicates.add(criteriaBuilder.between(
                    root.get("price"), 
                    min,
                    max
                ));
            }
            
            if (filterRequest.getSizes() != null && !filterRequest.getSizes().isEmpty()) {
                predicates.add(criteriaBuilder.isTrue(
                    criteriaBuilder.function(
                        "JSON_CONTAINS",
                        Boolean.class,
                        criteriaBuilder.function(
                            "JSON_ARRAY",
                            String.class,
                            root.get("sizes")
                        ),
                        criteriaBuilder.literal(filterRequest.getSizes())
                    )
                ));
            }
            
            if (filterRequest.getColors() != null && !filterRequest.getColors().isEmpty()) {
                predicates.add(criteriaBuilder.isTrue(
                    criteriaBuilder.function(
                        "JSON_CONTAINS",
                        Boolean.class,
                        criteriaBuilder.function(
                            "JSON_ARRAY",
                            String.class,
                            root.get("colors")
                        ),
                        criteriaBuilder.literal(filterRequest.getColors())
                    )
                ));
            }
            
            predicates.add(criteriaBuilder.isTrue(root.get("inStock")));
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    
    private Sort buildSort(String sortBy) {
        if (sortBy == null) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }
        
        return switch (sortBy) {
            case "price-asc" -> Sort.by(Sort.Direction.ASC, "price");
            case "price-desc" -> Sort.by(Sort.Direction.DESC, "price");
            case "popular" -> Sort.by(Sort.Direction.DESC, "rating");
            default -> Sort.by(Sort.Direction.DESC, "createdAt"); // newest
        };
    }
    
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setOriginalPrice(product.getOriginalPrice());
        dto.setDiscount(product.getDiscount());
        dto.setCategory(product.getCategory());
        dto.setGender(product.getGender());
        dto.setSizes(product.getSizes());
        dto.setColors(product.getColors());
        dto.setImages(product.getImages());
        dto.setRating(product.getRating());
        dto.setReviewCount(product.getReviewCount());
        dto.setInStock(product.getInStock());
        dto.setIsFeatured(product.getIsFeatured());
        dto.setIsNew(product.getIsNew());
        dto.setSubCategory(product.getSubCategory());
        dto.setStockQuantity(product.getStockQuantity());
        return dto;
    }
}