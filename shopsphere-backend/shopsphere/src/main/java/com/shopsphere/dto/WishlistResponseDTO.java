package com.shopsphere.dto;

import java.util.List;

public class WishlistResponseDTO {
    private Long userId;
    private List<WishlistProductDTO> products;
    private int itemCount;
    
    public WishlistResponseDTO() {}
    
    public WishlistResponseDTO(Long userId, List<WishlistProductDTO> products, int itemCount) {
        this.userId = userId;
        this.products = products;
        this.itemCount = itemCount;
    }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public List<WishlistProductDTO> getProducts() { return products; }
    public void setProducts(List<WishlistProductDTO> products) { this.products = products; }
    
    public int getItemCount() { return itemCount; }
    public void setItemCount(int itemCount) { this.itemCount = itemCount; }
}