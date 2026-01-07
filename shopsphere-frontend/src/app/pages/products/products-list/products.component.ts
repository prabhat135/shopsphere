// src/app/components/products/products.component.ts
import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../../core/singleton services/services/product.service';
import { Product, FilterOptions } from '../../../shared/models/product.model';
import { Navbar } from '../../../shared/components/navbar/navbar';
import { Footer } from '../../../shared/components/footer/footer';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-products',
  imports: [CommonModule, RouterModule, FormsModule, Navbar, Footer],
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit, OnDestroy {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  
  // Filter options
  filterOptions: FilterOptions = {
    categories: [],
    genders: [],
    priceRange: { min: 0, max: 10000 },
    sizes: [],
    colors: [],
    sortBy: 'newest'
  };

  // Available filter values with counts
  allCategories: { category: string, count: number }[] = [];
  allGenders: { gender: string, count: number }[] = [];
  allSizes: { size: string, count: number }[] = [];
  allColors: { color: string, count: number }[] = [];

  // UI state
  selectedGender: string | null = null;
  selectedCategory: string | null = null;
  isLoading = false;
  showFilterPanel = true;
  priceRangeValue = 10000;

  private routeSub!: Subscription;

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadInitialProducts();
    this.routeSub = this.route.queryParams.subscribe(params => {
      const gender = params['gender'] as string;
      const category = params['category'] as string;
      
      if (gender) {
        this.selectedGender = gender;
        this.filterOptions.genders = [gender];
      }
      
      if (category) {
        this.selectedCategory = category;
        this.filterOptions.categories = [category];
      }
      
      this.applyFilters();
    });
  }

  ngOnDestroy(): void {
    if (this.routeSub) {
      this.routeSub.unsubscribe();
    }
  }

  loadInitialProducts(): void {
    this.isLoading = true;
    this.productService.getAllProducts().subscribe({
      next: (products) => {
        this.products = products;
        this.filteredProducts = [...products];
        this.initializeFilterOptions();
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading products:', error);
        this.isLoading = false;
      }
    });
  }

  initializeFilterOptions(): void {
    // Initialize categories with counts
    const categoryCounts = new Map<string, number>();
    this.products.forEach(product => {
      categoryCounts.set(product.category, (categoryCounts.get(product.category) || 0) + 1);
    });
    
    this.allCategories = Array.from(categoryCounts.entries()).map(([category, count]) => ({
      category,
      count
    }));

    // Initialize genders with counts
    const genderCounts = new Map<string, number>();
    this.products.forEach(product => {
      genderCounts.set(product.gender, (genderCounts.get(product.gender) || 0) + 1);
    });
    
    this.allGenders = Array.from(genderCounts.entries()).map(([gender, count]) => ({
      gender,
      count
    }));

    // Initialize sizes with counts
    const sizeCounts = new Map<string, number>();
    this.products.forEach(product => {
      product.size.forEach(size => {
        sizeCounts.set(size, (sizeCounts.get(size) || 0) + 1);
      });
    });
    
    this.allSizes = Array.from(sizeCounts.entries()).map(([size, count]) => ({
      size,
      count
    }));

    // Initialize colors with counts
    const colorCounts = new Map<string, number>();
    this.products.forEach(product => {
      product.color.forEach(color => {
        colorCounts.set(color, (colorCounts.get(color) || 0) + 1);
      });
    });
    
    this.allColors = Array.from(colorCounts.entries()).map(([color, count]) => ({
      color,
      count
    }));
  }

  applyFilters(): void {
    this.isLoading = true;
    
    this.productService.filterProducts(this.filterOptions).subscribe({
      next: (products) => {
        this.filteredProducts = [...products];
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading products:', error);
        this.isLoading = false;
      }
    });
  }

  onGenderSelect(gender: string): void {
    if (this.filterOptions.genders.includes(gender)) {
      this.filterOptions.genders = this.filterOptions.genders.filter(g => g !== gender);
    } else {
      this.filterOptions.genders.push(gender);
    }
    this.applyFilters();
  }

  onCategorySelect(category: string): void {
    if (this.filterOptions.categories.includes(category)) {
      this.filterOptions.categories = this.filterOptions.categories.filter(c => c !== category);
    } else {
      this.filterOptions.categories.push(category);
    }
    this.applyFilters();
  }

  onSizeSelect(size: string): void {
    if (this.filterOptions.sizes.includes(size)) {
      this.filterOptions.sizes = this.filterOptions.sizes.filter(s => s !== size);
    } else {
      this.filterOptions.sizes.push(size);
    }
    this.applyFilters();
  }

  onColorSelect(color: string): void {
    if (this.filterOptions.colors.includes(color)) {
      this.filterOptions.colors = this.filterOptions.colors.filter(c => c !== color);
    } else {
      this.filterOptions.colors.push(color);
    }
    this.applyFilters();
  }

  onSortChange(sortBy: FilterOptions['sortBy']): void {
    this.filterOptions.sortBy = sortBy;
    this.applyFilters();
  }

  onPriceChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.priceRangeValue = parseInt(input.value);
    this.filterOptions.priceRange.max = this.priceRangeValue;
    this.applyFilters();
  }

  clearFilters(): void {
    this.filterOptions = {
      categories: [],
      genders: [],
      priceRange: { min: 0, max: 10000 },
      sizes: [],
      colors: [],
      sortBy: 'newest'
    };
    this.priceRangeValue = 10000;
    this.selectedGender = null;
    this.selectedCategory = null;
    this.applyFilters();
  }

  toggleFilterPanel(): void {
    this.showFilterPanel = !this.showFilterPanel;
  }

  navigateToProduct(id: number): void {
    this.router.navigate(['/product', id]);
  }

  getDiscountPercentage(originalPrice: number, price: number): number {
    return Math.round(((originalPrice - price) / originalPrice) * 100);
  }

  // Helper to get category display name
  getCategoryName(category: string): string {
    const names: Record<string, string> = {
      'SUMMER': 'Summer Wear',
      'WINTER': 'Winter Wear',
      'FORMAL': 'Formal Wear',
      'CASUAL': 'Casual Wear',
      'TRADITIONAL': 'Traditional Wear'
    };
    return names[category] || category;
  }

  // Helper to get gender display name
  getGenderName(gender: string): string {
    const names: Record<string, string> = {
      'WOMEN': 'Women',
      'MEN': 'Men',
      'KIDS': 'Kids',
      'UNISEX': 'Unisex'
    };
    return names[gender] || gender;
  }
}