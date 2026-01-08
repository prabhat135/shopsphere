package com.shopsphere.dto;

import java.time.LocalDateTime;

public class WishlistProductDTO {
    private ProductDTO product;
    private LocalDateTime addedAt;
    
    public WishlistProductDTO() {}
    
    public WishlistProductDTO(ProductDTO product, LocalDateTime addedAt) {
        this.product = product;
        this.addedAt = addedAt;
    }
    
    public ProductDTO getProduct() { return product; }
    public void setProduct(ProductDTO product) { this.product = product; }
    
    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }
}