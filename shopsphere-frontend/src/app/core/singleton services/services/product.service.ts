// src/app/services/product.service.ts (simplified version)
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Product,FilterOptions } from '../../../shared/models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private mockProducts: Product[] = [];

  constructor() {
    this.initializeMockProducts();
  }

  private initializeMockProducts(): void {
    this.mockProducts = [
      // ... (same mock products as before, but with simpler enum types)
      // Change enums to strings
      {
        id: 1,
        name: 'Floral Summer Dress',
        description: 'Lightweight floral print dress perfect for summer',
        price: 1499,
        category: 'SUMMER',
        gender: 'WOMEN',
        size: ['S', 'M', 'L', 'XL'],
        color: ['Pink', 'White', 'Blue'],
        images: ['products/women/summer/dress1.jpg'],
        rating: 4.5,
        reviewCount: 128,
        inStock: true,
        isFeatured: true,
        isNew: true
      },
      // ... add more products
    ];
  }

  getAllProducts(): Observable<Product[]> {
    return of(this.mockProducts);
  }

  getProductsByGender(gender: string): Observable<Product[]> {
    const filtered = this.mockProducts.filter(product => product.gender === gender);
    return of(filtered);
  }

  getProductsByCategory(category: string): Observable<Product[]> {
    const filtered = this.mockProducts.filter(product => product.category === category);
    return of(filtered);
  }

  getProductById(id: number): Observable<Product | undefined> {
    const product = this.mockProducts.find(p => p.id === id);
    return of(product);
  }

  filterProducts(options: Partial<FilterOptions>): Observable<Product[]> {
    let filtered = [...this.mockProducts];

    if (options.categories && options.categories.length > 0) {
      filtered = filtered.filter(p => options.categories!.includes(p.category));
    }

    if (options.genders && options.genders.length > 0) {
      filtered = filtered.filter(p => options.genders!.includes(p.gender));
    }

    if (options.priceRange) {
      filtered = filtered.filter(p => 
        p.price >= options.priceRange!.min && p.price <= options.priceRange!.max
      );
    }

    if (options.sizes && options.sizes.length > 0) {
      filtered = filtered.filter(p => 
        p.size.some(size => options.sizes!.includes(size))
      );
    }

    if (options.colors && options.colors.length > 0) {
      filtered = filtered.filter(p => 
        p.color.some(color => options.colors!.includes(color))
      );
    }

    // Sorting
    if (options.sortBy) {
      switch (options.sortBy) {
        case 'price-asc':
          filtered.sort((a, b) => a.price - b.price);
          break;
        case 'price-desc':
          filtered.sort((a, b) => b.price - a.price);
          break;
        case 'newest':
          filtered.sort((a, b) => b.id - a.id);
          break;
        case 'popular':
          filtered.sort((a, b) => b.rating - a.rating);
          break;
      }
    }

    return of(filtered);
  }
}