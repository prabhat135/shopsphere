package com.shopsphere.service;

import com.shopsphere.dto.*;
import com.shopsphere.model.*;
import com.shopsphere.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WishlistService {
    
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    
    public WishlistService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
    
    public WishlistResponseDTO getWishlist(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<WishlistProductDTO> productDTOs = user.getWishlist().stream()
            .map(this::convertToWishlistProductDTO)
            .collect(Collectors.toList());
        
        return new WishlistResponseDTO(userId, productDTOs, user.getWishlist().size());
    }
    
    public void addToWishlist(Long userId, Long productId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        if (user.getWishlist().contains(product)) {
            throw new RuntimeException("Product already in wishlist");
        }
        
        user.getWishlist().add(product);
        userRepository.save(user);
    }
    
    public void removeFromWishlist(Long userId, Long productId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        if (!user.getWishlist().contains(product)) {
            throw new RuntimeException("Product not in wishlist");
        }
        
        user.getWishlist().remove(product);
        userRepository.save(user);
    }
    
    public void clearWishlist(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.getWishlist().clear();
        userRepository.save(user);
    }
    
    public boolean isProductInWishlist(Long userId, Long productId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        return user.getWishlist().contains(product);
    }
    
    public int getWishlistCount(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        return user.getWishlist().size();
    }
    
    private WishlistProductDTO convertToWishlistProductDTO(Product product) {
        ProductDTO productDTO = convertToProductDTO(product);
        return new WishlistProductDTO(productDTO, LocalDateTime.now());
    }
    
    private ProductDTO convertToProductDTO(Product product) {
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