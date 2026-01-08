package com.shopsphere.dto;

public class WishlistCountResponse {
    private int count;
    
    public WishlistCountResponse() {}
    
    public WishlistCountResponse(int count) {
        this.count = count;
    }
    
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}