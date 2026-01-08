package com.shopsphere.service;

import com.shopsphere.dto.OrderDTO;
import com.shopsphere.dto.OrderItemDTO;
import com.shopsphere.model.*;
import com.shopsphere.repository.OrderRepository;
import com.shopsphere.repository.OrderItemRepository;
import com.shopsphere.repository.AddressRepository;
import com.shopsphere.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final ProductService productService;
    
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                       CartRepository cartRepository, AddressRepository addressRepository,
                       UserService userService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.productService = productService;
    }
    
    public Order createOrderFromCart(Long userId, Long addressId, PaymentMethod paymentMethod) {
        User user = userService.findById(userId);
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        
        Address address = addressRepository.findByIdAndUserId(addressId, userId)
            .orElseThrow(() -> new RuntimeException("Address not found"));
        
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(address);
        order.setPaymentMethod(paymentMethod);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setOrderStatus(OrderStatus.PENDING);
        
        // Calculate totals
        double subtotal = cart.getTotalPrice();
        double tax = subtotal * 0.18; // 18% GST
        double shipping = subtotal > 999 ? 0 : 50; // Free shipping above 999
        
        order.setSubtotal(subtotal);
        order.setTax(tax);
        order.setShippingCharge(shipping);
        order.setTotalAmount(subtotal + tax + shipping);
        order.setExpectedDeliveryDate(LocalDateTime.now().plusDays(7));
        
        // Create order items
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSize(cartItem.getSelectedSize());
            orderItem.setColor(cartItem.getSelectedColor());
            orderItem.setPrice(cartItem.getPrice());
            
            order.getItems().add(orderItem);
            
            // Update stock
            Product product = cartItem.getProduct();
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productService.updateProduct(product.getId(), product);
        }
        
        Order savedOrder = orderRepository.save(order);
        
        // Clear cart
        cart.getItems().clear();
        cartRepository.save(cart);
        
        return savedOrder;
    }
    
    public Order getOrderById(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        return order;
    }
    
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
    
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setOrderStatus(status);
        
        if (status == OrderStatus.CANCELLED) {
            order.setCancelledDate(LocalDateTime.now());
            // Restore stock
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                productService.updateProduct(product.getId(), product);
            }
        } else if (status == OrderStatus.DELIVERED) {
            order.setDeliveredDate(LocalDateTime.now());
            order.setPaymentStatus(PaymentStatus.COMPLETED);
        }
        
        return orderRepository.save(order);
    }
    
    public Order updatePaymentStatus(Long orderId, PaymentStatus status, String transactionId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setPaymentStatus(status);
        order.setTransactionId(transactionId);
        
        if (status == PaymentStatus.COMPLETED) {
            order.setOrderStatus(OrderStatus.CONFIRMED);
        }
        
        return orderRepository.save(order);
    }
    
    public OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setSubtotal(order.getSubtotal());
        dto.setTax(order.getTax());
        dto.setShippingCharge(order.getShippingCharge());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setTrackingNumber(order.getTrackingNumber());
        dto.setOrderDate(order.getOrderDate());
        dto.setExpectedDeliveryDate(order.getExpectedDeliveryDate());
        
        List<OrderItemDTO> itemDTOs = order.getItems().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        dto.setItems(itemDTOs);
        
        return dto;
    }
    
    private OrderItemDTO convertToDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(item.getId());
        dto.setProduct(productService.convertToDTO(item.getProduct()));
        dto.setQuantity(item.getQuantity());
        dto.setSize(item.getSize());
        dto.setColor(item.getColor());
        dto.setPrice(item.getPrice());
        
        return dto;
    }
}