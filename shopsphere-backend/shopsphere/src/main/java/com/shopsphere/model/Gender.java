package com.shopsphere.model;

public enum Gender {
    WOMEN("Women"),
    MEN("Men"),
    KIDS("Kids"),
    UNISEX("Unisex");
    
    private final String displayName;
    
    Gender(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
