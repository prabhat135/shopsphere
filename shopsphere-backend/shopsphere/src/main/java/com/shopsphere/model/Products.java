package com.shopsphere.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(name = "image_url")
    private String imageUrl;

    // TRADITIONAL, WESTERN, SEASONAL
    @Column(name = "style_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private StyleCategory styleCategory;

    // MEN, WOMEN, KIDS
    @Column(name = "gender_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderCategory genderCategory;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Boolean active = true;

    // ===== Constructors =====

    public Products() {}

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StyleCategory getStyleCategory() {
        return styleCategory;
    }

    public void setStyleCategory(StyleCategory styleCategory) {
        this.styleCategory = styleCategory;
    }

    public GenderCategory getGenderCategory() {
        return genderCategory;
    }

    public void setGenderCategory(GenderCategory genderCategory) {
        this.genderCategory = genderCategory;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
