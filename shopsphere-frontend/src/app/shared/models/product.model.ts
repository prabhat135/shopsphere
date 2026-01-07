// src/app/shared/models/product.model.ts
export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  originalPrice?: number;
  discount?: number;
  category: string; // 'SUMMER' | 'WINTER' | 'FORMAL' | 'CASUAL' | 'TRADITIONAL'
  gender: string; // 'WOMEN' | 'MEN' | 'KIDS'
  size: string[];
  color: string[];
  images: string[];
  rating: number;
  reviewCount: number;
  inStock: boolean;
  isFeatured: boolean;
  isNew: boolean;
  // Remove subCategory if not needed, or add it:
  subCategory?: string; // Optional field
}

export interface FilterOptions {
  categories: string[];
  genders: string[];
  priceRange: {
    min: number;
    max: number;
  };
  sizes: string[];
  colors: string[];
  sortBy: 'price-asc' | 'price-desc' | 'newest' | 'popular';
}

// Optional: If you want to use enums, define them separately
export enum ProductCategory {
  SUMMER = 'SUMMER',
  WINTER = 'WINTER',
  FORMAL = 'FORMAL',
  CASUAL = 'CASUAL',
  TRADITIONAL = 'TRADITIONAL'
}

export enum Gender {
  WOMEN = 'WOMEN',
  MEN = 'MEN',
  KIDS = 'KIDS',
  UNISEX = 'UNISEX'
}