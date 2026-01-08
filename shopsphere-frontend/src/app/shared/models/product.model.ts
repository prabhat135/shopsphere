// shared/models/product.model.ts
export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  originalPrice?: number;
  discount?: number;
  category: string; // 'SUMMER' | 'WINTER' | 'FORMAL' | 'CASUAL' | 'TRADITIONAL'
  gender: string; // 'WOMEN' | 'MEN' | 'KIDS' | 'UNISEX'
  sizes: string[];
  colors: string[];
  images: string[];
  rating: number;
  reviewCount: number;
  inStock: boolean;
  isFeatured: boolean;
  isNew: boolean;
  subCategory?: string;
  stockQuantity: number;
  discountPrice?: number;   // For cart calculations (if different from price)
  brand?: string;   
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

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}