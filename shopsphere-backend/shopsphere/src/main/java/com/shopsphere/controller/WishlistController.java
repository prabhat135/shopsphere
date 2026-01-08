package com.shopsphere.controller;

import com.shopsphere.dto.*;
import com.shopsphere.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    
    private final WishlistService wishlistService;
    
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }
    
    @GetMapping
    public ResponseEntity<WishlistResponseDTO> getWishlist(@AuthenticationPrincipal Long userId) {
        WishlistResponseDTO wishlist = wishlistService.getWishlist(userId);
        return ResponseEntity.ok(wishlist);
    }
    
    @PostMapping("/add/{productId}")
    public ResponseEntity<Map<String, String>> addToWishlist(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long productId) {
        
        wishlistService.addToWishlist(userId, productId);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product added to wishlist");
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Map<String, String>> removeFromWishlist(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long productId) {
        
        wishlistService.removeFromWishlist(userId, productId);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Product removed from wishlist");
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<Map<String, String>> clearWishlist(@AuthenticationPrincipal Long userId) {
        wishlistService.clearWishlist(userId);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Wishlist cleared");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/check/{productId}")
    public ResponseEntity<Map<String, Boolean>> checkProductInWishlist(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long productId) {
        
        boolean inWishlist = wishlistService.isProductInWishlist(userId, productId);
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("inWishlist", inWishlist);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getWishlistCount(@AuthenticationPrincipal Long userId) {
        int count = wishlistService.getWishlistCount(userId);
        
        Map<String, Integer> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
}