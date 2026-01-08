package com.shopsphere.repository;

import com.shopsphere.model.CartItem;
import com.shopsphere.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    // Find all cart items for a specific cart
    List<CartItem> findByCart(Cart cart);
    
    // Find all cart items for a specific cart ID
    List<CartItem> findByCartId(Long cartId);
    
    // Find specific cart item by cart ID and product ID, size, and color
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId " +
           "AND ci.product.id = :productId " +
           "AND ci.selectedSize = :size " +
           "AND ci.selectedColor = :color")
    Optional<CartItem> findByCartIdAndProductIdAndSizeAndColor(
            @Param("cartId") Long cartId,
            @Param("productId") Long productId,
            @Param("size") String size,
            @Param("color") String color);
    
    // Find cart item by ID and cart ID (for user validation)
    Optional<CartItem> findByIdAndCartId(Long id, Long cartId);
    
    // Find cart item by ID and cart user ID
    @Query("SELECT ci FROM CartItem ci WHERE ci.id = :itemId AND ci.cart.user.id = :userId")
    Optional<CartItem> findByIdAndUserId(@Param("itemId") Long itemId, @Param("userId") Long userId);
    
    // Check if a product exists in any cart
    boolean existsByProductId(Long productId);
    
    // Get total items in cart
    @Query("SELECT COUNT(ci) FROM CartItem ci WHERE ci.cart.id = :cartId")
    Integer countByCartId(@Param("cartId") Long cartId);
    
    // Get total quantity in cart
    @Query("SELECT SUM(ci.quantity) FROM CartItem ci WHERE ci.cart.id = :cartId")
    Integer sumQuantityByCartId(@Param("cartId") Long cartId);
    
    // Get cart total price
    @Query("SELECT SUM(ci.price * ci.quantity) FROM CartItem ci WHERE ci.cart.id = :cartId")
    Double getCartTotal(@Param("cartId") Long cartId);
    
    // Delete all cart items for a specific cart
    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteByCartId(@Param("cartId") Long cartId);
    
    // Delete specific cart item by cart ID and product ID
    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id = :productId")
    void deleteByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);
    
    // Update quantity for a cart item
    @Transactional
    @Modifying
    @Query("UPDATE CartItem ci SET ci.quantity = :quantity WHERE ci.id = :itemId")
    void updateQuantity(@Param("itemId") Long itemId, @Param("quantity") Integer quantity);
    
    // Check if a specific product variant is in cart
    @Query("SELECT CASE WHEN COUNT(ci) > 0 THEN true ELSE false END " +
           "FROM CartItem ci WHERE ci.cart.id = :cartId " +
           "AND ci.product.id = :productId " +
           "AND ci.selectedSize = :size " +
           "AND ci.selectedColor = :color")
    boolean existsInCart(@Param("cartId") Long cartId, 
                        @Param("productId") Long productId,
                        @Param("size") String size,
                        @Param("color") String color);
}