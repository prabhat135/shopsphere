// FilterRequestDTO.java - Fixed version
package com.shopsphere.dto;

import lombok.Data;
import java.util.List;

@Data
public class FilterRequestDTO {
    private List<String> categories;
    private List<String> genders;
    private PriceRange priceRange;
    private List<String> sizes;
    private List<String> colors;
    private String sortBy; // "price-asc", "price-desc", "newest", "popular"
    
    public FilterRequestDTO() {
		super();
	}

	public FilterRequestDTO(List<String> categories, List<String> genders, PriceRange priceRange, List<String> sizes,
			List<String> colors, String sortBy) {
		super();
		this.categories = categories;
		this.genders = genders;
		this.priceRange = priceRange;
		this.sizes = sizes;
		this.colors = colors;
		this.sortBy = sortBy;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getGenders() {
		return genders;
	}

	public void setGenders(List<String> genders) {
		this.genders = genders;
	}

	public PriceRange getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(PriceRange priceRange) {
		this.priceRange = priceRange;
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

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	@Data
    public static class PriceRange {
        private Double min = 0.0;
        private Double max = 10000.0;
        
        // Getter and Setter methods
        public Double getMin() {
            return min != null ? min : 0.0;
        }
        
        public void setMin(Double min) {
            this.min = min;
        }
        
        public Double getMax() {
            return max != null ? max : 10000.0;
        }
        
        public void setMax(Double max) {
            this.max = max;
        }
    }
}