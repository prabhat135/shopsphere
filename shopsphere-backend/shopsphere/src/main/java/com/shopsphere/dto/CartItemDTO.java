package com.shopsphere.dto;

public class CartItemDTO {
    private Long id;
    private ProductDTO product;
    private Integer quantity;
    private String selectedSize;
    private String selectedColor;
    private Double price;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public ProductDTO getProduct() { return product; }
    public void setProduct(ProductDTO product) { this.product = product; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public String getSelectedSize() { return selectedSize; }
    public void setSelectedSize(String selectedSize) { this.selectedSize = selectedSize; }
    
    public String getSelectedColor() { return selectedColor; }
    public void setSelectedColor(String selectedColor) { this.selectedColor = selectedColor; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}