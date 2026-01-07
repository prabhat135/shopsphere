package com.shopsphere.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(name = "original_price")
    private Double originalPrice;
    
    private Integer discount;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    
    @ElementCollection
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "size")
    private List<String> sizes;
    
    @ElementCollection
    @CollectionTable(name = "product_colors", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "color")
    private List<String> colors;
    
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> images;
    
    private Double rating = 0.0;
    
    @Column(name = "review_count")
    private Integer reviewCount = 0;
    
    @Column(name = "in_stock")
    private Boolean inStock = true;
    
    @Column(name = "is_featured")
    private Boolean isFeatured = false;
    
    @Column(name = "is_new")
    private Boolean isNew = true;
    
    @Column(name = "sub_category")
    private String subCategory;
    
    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;
    
    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public List<String> getSizes() {
		return sizes;
	}

	public void setSizes(List<String> sizes) {
		this.sizes = sizes;
	}

	public List<String> getColors() {
		return colors;
	}

	public void setColors(List<String> colors) {
		this.colors = colors;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Integer getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Integer reviewCount) {
		this.reviewCount = reviewCount;
	}

	public Boolean getInStock() {
		return inStock;
	}

	public void setInStock(Boolean inStock) {
		this.inStock = inStock;
	}

	public Boolean getIsFeatured() {
		return isFeatured;
	}

	public void setIsFeatured(Boolean isFeatured) {
		this.isFeatured = isFeatured;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public java.time.LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(java.time.LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Product(Long id, String name, String description, Double price, Double originalPrice, Integer discount,
			Category category, Gender gender, List<String> sizes, List<String> colors, List<String> images,
			Double rating, Integer reviewCount, Boolean inStock, Boolean isFeatured, Boolean isNew, String subCategory,
			Integer stockQuantity, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.originalPrice = originalPrice;
		this.discount = discount;
		this.category = category;
		this.gender = gender;
		this.sizes = sizes;
		this.colors = colors;
		this.images = images;
		this.rating = rating;
		this.reviewCount = reviewCount;
		this.inStock = inStock;
		this.isFeatured = isFeatured;
		this.isNew = isNew;
		this.subCategory = subCategory;
		this.stockQuantity = stockQuantity;
		this.createdAt = createdAt;
	}

	public Product() {
		super();
	}
    
}