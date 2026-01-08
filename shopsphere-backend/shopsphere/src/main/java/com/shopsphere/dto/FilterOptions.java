package com.shopsphere.dto;

import com.shopsphere.model.ProductCategory;
import com.shopsphere.model.Gender;

import java.util.List;

public class FilterOptions {

    private List<ProductCategory> categories;
    private List<Gender> genders;

    private PriceRange priceRange;

    private List<String> sizes;
    private List<String> colors;

    private String sortBy; // price-asc, price-desc, newest, popular

    // ---------- INNER CLASS ----------
    public static class PriceRange {
        private Double min;
        private Double max;

        public Double getMin() { return min; }
        public void setMin(Double min) { this.min = min; }

        public Double getMax() { return max; }
        public void setMax(Double max) { this.max = max; }
    }

    // ---------- GETTERS & SETTERS ----------
    public List<ProductCategory> getCategories() { return categories; }
    public void setCategories(List<ProductCategory> categories) { this.categories = categories; }

    public List<Gender> getGenders() { return genders; }
    public void setGenders(List<Gender> genders) { this.genders = genders; }

    public PriceRange getPriceRange() { return priceRange; }
    public void setPriceRange(PriceRange priceRange) { this.priceRange = priceRange; }

    public List<String> getSizes() { return sizes; }
    public void setSizes(List<String> sizes) { this.sizes = sizes; }

    public List<String> getColors() { return colors; }
    public void setColors(List<String> colors) { this.colors = colors; }

    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }
}
