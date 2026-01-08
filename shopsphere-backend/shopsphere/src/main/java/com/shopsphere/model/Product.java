package com.shopsphere.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private Double price;
    
    private Double originalPrice;
    private Double discount;
    
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @ElementCollection
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "size")
    private Set<String> sizes = new HashSet<>();
    
    @ElementCollection
    @CollectionTable(name = "product_colors", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "color")
    private Set<String> colors = new HashSet<>();
    
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private Set<String> images = new HashSet<>();
    
    private Double rating;
    private Integer reviewCount;
    private Integer stockQuantity;
    private Boolean featured;
    private Boolean isNew;
    private String subCategory;
    
    @OneToMany(mappedBy = "product")
    private Set<CartItem> cartItems = new HashSet<>();
    
    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems = new HashSet<>();
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (stockQuantity == null) stockQuantity = 100;
        if (reviewCount == null) reviewCount = 0;
        if (rating == null) rating = 4.5;
        if (featured == null) featured = false;
        if (isNew == null) isNew = true;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
 // Add this field to existing Product.java (NO OTHER CHANGES):
    @ManyToMany(mappedBy = "wishlist", fetch = FetchType.LAZY)
    private Set<User> usersWithWishlist = new HashSet<>();

    public Set<User> getUsersWithWishlist() { return usersWithWishlist; }
    public void setUsersWithWishlist(Set<User> usersWithWishlist) { this.usersWithWishlist = usersWithWishlist; }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public Double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(Double originalPrice) { this.originalPrice = originalPrice; }
    
    public Double getDiscount() { return discount; }
    public void setDiscount(Double discount) { this.discount = discount; }
    
    public ProductCategory getCategory() { return category; }
    public void setCategory(ProductCategory category) { this.category = category; }
    
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    
    public Set<String> getSizes() { return sizes; }
    public void setSizes(Set<String> sizes) { this.sizes = sizes; }
    
    public Set<String> getColors() { return colors; }
    public void setColors(Set<String> colors) { this.colors = colors; }
    
    public Set<String> getImages() { return images; }
    public void setImages(Set<String> images) { this.images = images; }
    
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    
    public Integer getReviewCount() { return reviewCount; }
    public void setReviewCount(Integer reviewCount) { this.reviewCount = reviewCount; }
    
    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    
    public Boolean getFeatured() { return featured; }
    public void setFeatured(Boolean featured) { this.featured = featured; }
    
    public Boolean getIsNew() { return isNew; }
    public void setIsNew(Boolean isNew) { this.isNew = isNew; }
    
    public String getSubCategory() { return subCategory; }
    public void setSubCategory(String subCategory) { this.subCategory = subCategory; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}