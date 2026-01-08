package com.shopsphere.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemDTO {
    private Long id;
    private ProductDTO product;
    private Integer quantity;
    private String size;
    private String color;
    private Double price;
    
    // Constructors
    public OrderItemDTO() {}
    
    public OrderItemDTO(Long id, ProductDTO product, Integer quantity, String size, String color, Double price) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.price = price;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public ProductDTO getProduct() { return product; }
    public void setProduct(ProductDTO product) { this.product = product; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}