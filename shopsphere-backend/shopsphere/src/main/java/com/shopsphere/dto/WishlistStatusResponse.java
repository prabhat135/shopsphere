package com.shopsphere.dto;

public class WishlistStatusResponse {
    private boolean inWishlist;
    private Long productId;
    
    public WishlistStatusResponse() {}
    
    public WishlistStatusResponse(boolean inWishlist, Long productId) {
        this.inWishlist = inWishlist;
        this.productId = productId;
    }
    
    public boolean isInWishlist() { return inWishlist; }
    public void setInWishlist(boolean inWishlist) { this.inWishlist = inWishlist; }
    
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
}