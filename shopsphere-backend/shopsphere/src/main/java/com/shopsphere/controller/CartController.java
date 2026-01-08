package com.shopsphere.controller;

import com.shopsphere.dto.CartDTO;
import com.shopsphere.util.SecurityUtils;
import com.shopsphere.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList; 

@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    private final CartService cartService;
    
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @GetMapping("/public")
    public ResponseEntity<CartDTO> getPublicCart() {
        // Create a mock cart for testing
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(999L);
        cartDTO.setTotalPrice(0.0);
        cartDTO.setItems(new ArrayList<>());
        return ResponseEntity.ok(cartDTO);
    }
    @GetMapping
    public ResponseEntity<CartDTO> getCart() {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        
        try {
            CartDTO cart = cartService.getCartDTO(userId);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            @RequestParam String size,
            @RequestParam String color) {
        
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        
        if (quantity <= 0) {
            return ResponseEntity.badRequest().body("Quantity must be greater than 0");
        }
        
        if (size == null || size.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Size is required");
        }
        
        if (color == null || color.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Color is required");
        }
        
        try {
            cartService.addToCart(userId, productId, quantity, size.trim(), color.trim());
            return ResponseEntity.ok("Product added to cart successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to add product to cart");
        }
    }
    
    @PutMapping("/update/{itemId}")
    public ResponseEntity<String> updateCartItem(
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        
        if (quantity <= 0) {
            return ResponseEntity.badRequest().body("Quantity must be greater than 0");
        }
        
        try {
            cartService.updateCartItem(userId, itemId, quantity);
            return ResponseEntity.ok("Cart item updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update cart item");
        }
    }
    
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long itemId) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        
        try {
            cartService.removeFromCart(userId, itemId);
            return ResponseEntity.ok("Item removed from cart successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to remove item from cart");
        }
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        
        try {
            cartService.clearCart(userId);
            return ResponseEntity.ok("Cart cleared successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to clear cart");
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<Integer> getCartItemCount() {
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.ok(0);
        }
        
        try {
            Integer count = cartService.getCartItemCount(userId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.ok(0);
        }
    }
    
    @GetMapping("/check/{productId}")
    public ResponseEntity<Boolean> isProductInCart(
            @PathVariable Long productId,
            @RequestParam String size,
            @RequestParam String color) {
        
        Long userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.ok(false);
        }
        
        try {
            boolean isInCart = cartService.isProductInCart(userId, productId, size, color);
            return ResponseEntity.ok(isInCart);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}