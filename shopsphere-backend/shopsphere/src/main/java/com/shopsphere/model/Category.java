package com.shopsphere.model;

public enum Category {
    SUMMER("Summer Wear"),
    WINTER("Winter Wear"),
    FORMAL("Formal Wear"),
    CASUAL("Casual Wear"),
    TRADITIONAL("Traditional Wear");
    
    private final String displayName;
    
    Category(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}