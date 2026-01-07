package com.shopsphere.dto;

import com.shopsphere.model.Category;
import com.shopsphere.model.Gender;
import lombok.Data;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double originalPrice;
    private Integer discount;
    private Category category;
    private Gender gender;
    private List<String> sizes;
    private List<String> colors;
    private List<String> images;
    private Double rating;
    private Integer reviewCount;
    private Boolean inStock;
    private Boolean isFeatured;
    private Boolean isNew;
    private String subCategory;
    private Integer stockQuantity;
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
	public ProductDTO() {
		super();
	}
	public ProductDTO(Long id, String name, String description, Double price, Double originalPrice, Integer discount,
			Category category, Gender gender, List<String> sizes, List<String> colors, List<String> images,
			Double rating, Integer reviewCount, Boolean inStock, Boolean isFeatured, Boolean isNew, String subCategory,
			Integer stockQuantity) {
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
	}
    
    
}