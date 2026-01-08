package com.shopsphere.controller;

import com.shopsphere.dto.OrderDTO;
import com.shopsphere.model.Order;
import com.shopsphere.model.PaymentMethod;
import com.shopsphere.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(
            @AuthenticationPrincipal Long userId,
            @RequestParam Long addressId,
            @RequestParam PaymentMethod paymentMethod) {
        
        Order order = orderService.createOrderFromCart(userId, addressId, paymentMethod);
        return ResponseEntity.ok(orderService.convertToDTO(order));
    }
    
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getUserOrders(@AuthenticationPrincipal Long userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        List<OrderDTO> dtos = orders.stream()
            .map(orderService::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long orderId) {
        
        Order order = orderService.getOrderById(userId, orderId);
        return ResponseEntity.ok(orderService.convertToDTO(order));
    }
    
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderDTO> cancelOrder(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long orderId) {
        
        Order order = orderService.updateOrderStatus(orderId, com.shopsphere.model.OrderStatus.CANCELLED);
        return ResponseEntity.ok(orderService.convertToDTO(order));
    }
    
    @PostMapping("/{orderId}/payment")
    public ResponseEntity<OrderDTO> processPayment(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long orderId,
            @RequestParam String transactionId) {
        
        Order order = orderService.updatePaymentStatus(
            orderId, 
            com.shopsphere.model.PaymentStatus.COMPLETED, 
            transactionId
        );
        return ResponseEntity.ok(orderService.convertToDTO(order));
    }
}