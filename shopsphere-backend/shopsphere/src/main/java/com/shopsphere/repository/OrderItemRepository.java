package com.shopsphere.repository;

import com.shopsphere.model.OrderItem;
import com.shopsphere.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    // Find all order items for a specific order
    List<OrderItem> findByOrder(Order order);
    
    // Find all order items for a specific order ID
    List<OrderItem> findByOrderId(Long orderId);
    
    // Find all order items for a specific product
    List<OrderItem> findByProductId(Long productId);
    
    // Get total quantity sold for a product
    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.product.id = :productId")
    Integer getTotalQuantitySold(@Param("productId") Long productId);
    
    // Get all order items for multiple orders
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id IN :orderIds")
    List<OrderItem> findByOrderIds(@Param("orderIds") List<Long> orderIds);
    
    // Check if a product exists in any order
    boolean existsByProductId(Long productId);
    
    // Find top selling products
    @Query("SELECT oi.product.id, SUM(oi.quantity) as totalSold FROM OrderItem oi " +
           "GROUP BY oi.product.id ORDER BY totalSold DESC")
    List<Object[]> findTopSellingProducts();
    
    // Delete all order items for a specific order
    void deleteByOrderId(Long orderId);
}