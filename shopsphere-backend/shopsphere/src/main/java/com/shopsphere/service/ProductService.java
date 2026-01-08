package com.shopsphere.service;

import com.shopsphere.dto.ProductDTO;
import com.shopsphere.model.Product;
import com.shopsphere.model.ProductCategory;
import com.shopsphere.model.Gender;
import com.shopsphere.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    
    public List<Product> getProductsByCategory(ProductCategory category) {
        return productRepository.findByCategory(category);
    }
    
    public List<Product> getProductsByGender(Gender gender) {
        return productRepository.findByGender(gender);
    }
    
    public List<Product> getFeaturedProducts() {
        return productRepository.findByFeaturedTrue();
    }
    
    public List<Product> getNewArrivals() {
        return productRepository.findByIsNewTrue();
    }
    
    public List<Product> filterProducts(ProductCategory category, Gender gender, 
                                        Double minPrice, Double maxPrice) {
        return productRepository.filterProducts(category, gender, minPrice, maxPrice);
    }
    
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    
    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = getProductById(id);
        
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setCategory(updatedProduct.getCategory());
        product.setGender(updatedProduct.getGender());
        product.setSizes(updatedProduct.getSizes());
        product.setColors(updatedProduct.getColors());
        product.setStockQuantity(updatedProduct.getStockQuantity());
        
        return productRepository.save(product);
    }
    
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    // Convert Entity to DTO
    public ProductDTO convertToDTO(Product product) {
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
        dto.setStockQuantity(product.getStockQuantity());
        dto.setFeatured(product.getFeatured());
        dto.setIsNew(product.getIsNew());
        dto.setSubCategory(product.getSubCategory());
        
        return dto;
    }
}