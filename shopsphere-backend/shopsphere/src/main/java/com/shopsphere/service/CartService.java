package com.shopsphere.service;

import com.shopsphere.dto.CartDTO;
import com.shopsphere.dto.CartItemDTO;
import com.shopsphere.model.*;
import com.shopsphere.repository.CartRepository;
import com.shopsphere.repository.CartItemRepository;
import com.shopsphere.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {
    
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final ProductService productService;
    
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
                      ProductRepository productRepository, UserService userService,
                      ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.productService = productService;
    }
    
    public Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
            .orElseGet(() -> createCart(userId));
    }
    
    private Cart createCart(Long userId) {
        User user = userService.findById(userId);
        
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(0.0);
        
        return cartRepository.save(cart);
    }
    
    public CartItem addToCart(Long userId, Long productId, Integer quantity, 
                             String size, String color) {
        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        
        // Validate product stock
        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock. Only " + product.getStockQuantity() + " items available");
        }
        
        // Validate size and color
        if (!product.getSizes().contains(size)) {
            throw new RuntimeException("Invalid size. Available sizes: " + String.join(", ", product.getSizes()));
        }
        
        if (!product.getColors().contains(color)) {
            throw new RuntimeException("Invalid color. Available colors: " + String.join(", ", product.getColors()));
        }
        
        // Check if item already exists in cart
        Optional<CartItem> existingItem = cartItemRepository
            .findByCartIdAndProductIdAndSizeAndColor(cart.getId(), productId, size, color);
        
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + quantity;
            
            // Check if new quantity exceeds stock
            if (product.getStockQuantity() < newQuantity) {
                throw new RuntimeException("Cannot add more items. Maximum available: " + product.getStockQuantity());
            }
            
            item.setQuantity(newQuantity);
            CartItem savedItem = cartItemRepository.save(item);
            updateCartTotal(cart.getId());
            return savedItem;
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setSelectedSize(size);
            newItem.setSelectedColor(color);
            newItem.setPrice(product.getPrice());
            
            CartItem savedItem = cartItemRepository.save(newItem);
            updateCartTotal(cart.getId());
            return savedItem;
        }
    }
    
    public void updateCartItem(Long userId, Long itemId, Integer quantity) {
        CartItem item = cartItemRepository.findByIdAndUserId(itemId, userId)
            .orElseThrow(() -> new RuntimeException("Cart item not found or unauthorized"));
        
        if (quantity < 1) {
            throw new RuntimeException("Quantity must be at least 1");
        }
        
        // Check stock availability
        Product product = item.getProduct();
        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock. Only " + product.getStockQuantity() + " items available");
        }
        
        if (quantity == 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }
        
        updateCartTotal(item.getCart().getId());
    }
    
    public void removeFromCart(Long userId, Long itemId) {
        CartItem item = cartItemRepository.findByIdAndUserId(itemId, userId)
            .orElseThrow(() -> new RuntimeException("Cart item not found or unauthorized"));
        
        cartItemRepository.delete(item);
        updateCartTotal(item.getCart().getId());
    }
    
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cartItemRepository.deleteByCartId(cart.getId());
        
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }
    
    public CartDTO getCartDTO(Long userId) {
        Cart cart = getOrCreateCart(userId);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        
        // Update cart total
        updateCartTotal(cart.getId());
        
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setTotalPrice(cart.getTotalPrice());
        
        List<CartItemDTO> itemDTOs = cartItems.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        dto.setItems(itemDTOs);
        
        return dto;
    }
    
    public Integer getCartItemCount(Long userId) {
        Cart cart = getOrCreateCart(userId);
        Integer totalQuantity = cartItemRepository.sumQuantityByCartId(cart.getId());
        return totalQuantity != null ? totalQuantity : 0;
    }
    
    private void updateCartTotal(Long cartId) {
        Double cartTotal = cartItemRepository.getCartTotal(cartId);
        final Double finalTotal = cartTotal != null ? cartTotal : 0.0;
        
        cartRepository.findById(cartId).ifPresent(cart -> {
            cart.setTotalPrice(finalTotal);
            cartRepository.save(cart);
        });
    }
    
    private CartItemDTO convertToDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        dto.setProduct(productService.convertToDTO(item.getProduct()));
        dto.setQuantity(item.getQuantity());
        dto.setSelectedSize(item.getSelectedSize());
        dto.setSelectedColor(item.getSelectedColor());
        dto.setPrice(item.getPrice());
        
        // Calculate item total
        Double itemTotal = item.getPrice() * item.getQuantity();
        dto.setPrice(itemTotal);
        
        return dto;
    }
    
    public boolean isProductInCart(Long userId, Long productId, String size, String color) {
        Cart cart = getOrCreateCart(userId);
        return cartItemRepository.existsInCart(cart.getId(), productId, size, color);
    }
    
    public void mergeCarts(Long userId, List<CartItemDTO> items) {
        Cart cart = getOrCreateCart(userId);
        
        for (CartItemDTO itemDTO : items) {
            // Check if product exists
            Product product = productRepository.findById(itemDTO.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + itemDTO.getProduct().getId()));
            
            // Check if item already exists in cart
            Optional<CartItem> existingItem = cartItemRepository
                .findByCartIdAndProductIdAndSizeAndColor(
                    cart.getId(), 
                    product.getId(), 
                    itemDTO.getSelectedSize(), 
                    itemDTO.getSelectedColor()
                );
            
            if (existingItem.isPresent()) {
                CartItem item = existingItem.get();
                int newQuantity = item.getQuantity() + itemDTO.getQuantity();
                
                if (product.getStockQuantity() >= newQuantity) {
                    item.setQuantity(newQuantity);
                    cartItemRepository.save(item);
                } else {
                    // Set to maximum available
                    item.setQuantity(product.getStockQuantity());
                    cartItemRepository.save(item);
                }
            } else {
                if (product.getStockQuantity() >= itemDTO.getQuantity()) {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(itemDTO.getQuantity());
                    newItem.setSelectedSize(itemDTO.getSelectedSize());
                    newItem.setSelectedColor(itemDTO.getSelectedColor());
                    newItem.setPrice(product.getPrice());
                    
                    cartItemRepository.save(newItem);
                } else {
                    // Add maximum available
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(product.getStockQuantity());
                    newItem.setSelectedSize(itemDTO.getSelectedSize());
                    newItem.setSelectedColor(itemDTO.getSelectedColor());
                    newItem.setPrice(product.getPrice());
                    
                    cartItemRepository.save(newItem);
                }
            }
        }
        
        updateCartTotal(cart.getId());
    }
    
    public Double calculateCartTotal(Long userId) {
        Cart cart = getOrCreateCart(userId);
        Double total = cartItemRepository.getCartTotal(cart.getId());
        return total != null ? total : 0.0;
    }
    
    public Integer getDistinctItemCount(Long userId) {
        Cart cart = getOrCreateCart(userId);
        Integer count = cartItemRepository.countByCartId(cart.getId());
        return count != null ? count : 0;
    }
    
    public void validateCartStock(Long userId) {
        Cart cart = getOrCreateCart(userId);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            if (product.getStockQuantity() < item.getQuantity()) {
                // Adjust quantity to available stock
                if (product.getStockQuantity() == 0) {
                    cartItemRepository.delete(item);
                } else {
                    item.setQuantity(product.getStockQuantity());
                    cartItemRepository.save(item);
                }
            }
        }
        
        updateCartTotal(cart.getId());
    }
    
    public CartItem getCartItem(Long userId, Long itemId) {
        return cartItemRepository.findByIdAndUserId(itemId, userId)
            .orElseThrow(() -> new RuntimeException("Cart item not found or unauthorized"));
    }
}