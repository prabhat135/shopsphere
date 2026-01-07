// home.component.ts
import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../../shared/components/navbar/navbar';
import { Footer } from '../../shared/components/footer/footer';
import { ProductService } from '../../core/singleton services/services/product.service';
import { ApiResponse } from '../../shared/models/product.model';

interface TrendingProduct {
  productId: number;
  imageUrl: string;
  name: string;
  price?: number;
  category?: string;
}

interface HomeProduct {
  id: number;
  name: string;
  price: number;
  originalPrice?: number;
  discount?: number;
  image: string;
  category: string;
  rating?: number;
  reviewCount?: number;
  sizes?: string[];
  isNew?: boolean;
}

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    HttpClientModule,
    FormsModule,
    Navbar,
    Footer
  ],
  templateUrl: './home.html',
  styleUrls: ['./home.css']
})
export class Home implements OnInit, OnDestroy {
  currentSlide = 0;
  trendingProducts: TrendingProduct[] = [];
  products: HomeProduct[] = [];
  isLoading = false;
  carouselInterval: any;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadTrendingProducts();
    this.loadFeaturedProducts();
    this.startCarouselAutoSlide();
  }

  ngOnDestroy(): void {
    this.stopCarouselAutoSlide();
  }

  /**
   * Load trending products from API
   */
  loadTrendingProducts(): void {
    this.isLoading = true;
    
    this.productService.getTrendingProducts().subscribe({
      next: (response: ApiResponse<any[]>) => {
        if (response.success && response.data) {
          this.trendingProducts = response.data.map(product => ({
            productId: product.id,
            imageUrl: product.images?.[0] || 'assets/default-product.jpg',
            name: product.name,
            price: product.price,
            category: product.category
          }));
        } else {
          // Fallback to mock data if API fails
          this.loadMockTrendingProducts();
        }
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading trending products:', error);
        this.loadMockTrendingProducts();
        this.isLoading = false;
      }
    });
  }

  /**
   * Load featured products (new arrivals) from API
   */
  loadFeaturedProducts(): void {
    this.productService.getNewArrivals().subscribe({
      next: (response: ApiResponse<any[]>) => {
        if (response.success && response.data) {
          this.products = response.data.slice(0, 8).map(product => ({
            id: product.id,
            name: product.name,
            price: product.price,
            originalPrice: product.originalPrice,
            discount: product.discount,
            image: product.images?.[0] || 'assets/default-product.jpg',
            category: this.getCategoryDisplayName(product.category),
            rating: product.rating,
            reviewCount: product.reviewCount,
            sizes: product.sizes,
            isNew: product.isNew
          }));
        } else {
          // Fallback to mock data if API fails
          this.loadMockProducts();
        }
      },
      error: (error) => {
        console.error('Error loading featured products:', error);
        this.loadMockProducts();
      }
    });
  }

  /**
   * Load mock trending products as fallback
   */
  private loadMockTrendingProducts(): void {
    this.trendingProducts = [
      { 
        productId: 1, 
        imageUrl: 'assets/homePage_img/trend1.jpg', 
        name: 'Floral Summer Dress',
        price: 1499
      },
      { 
        productId: 2, 
        imageUrl: 'assets/homePage_img/trend2.jpg', 
        name: 'Wool Winter Coat',
        price: 2999
      },
      { 
        productId: 3, 
        imageUrl: 'assets/homePage_img/trend3.jpg', 
        name: 'Formal Business Suit',
        price: 4999
      },
      { 
        productId: 4, 
        imageUrl: 'assets/homePage_img/trend4.jpg', 
        name: 'Casual Denim Jacket',
        price: 1999
      },
      { 
        productId: 5, 
        imageUrl: 'assets/homePage_img/trend5.jpg', 
        name: 'Traditional Silk Saree',
        price: 3999
      },
      { 
        productId: 6, 
        imageUrl: 'assets/homePage_img/trend6.jpg', 
        name: 'Kids Winter Jacket',
        price: 1299
      },
      { 
        productId: 7, 
        imageUrl: 'assets/homePage_img/trend7.jpg', 
        name: 'Summer T-Shirt',
        price: 799
      },
      { 
        productId: 8, 
        imageUrl: 'assets/homePage_img/trend8.jpg', 
        name: 'Formal Leather Shoes',
        price: 2499
      },
      { 
        productId: 9, 
        imageUrl: 'assets/homePage_img/trend9.jpg', 
        name: 'Evening Gown',
        price: 3499
      },
      { 
        productId: 10, 
        imageUrl: 'assets/homePage_img/trend10.jpg', 
        name: 'Sports Jacket',
        price: 1799
      }
    ];
  }

  /**
   * Load mock products as fallback
   */
  private loadMockProducts(): void {
    this.products = [
      {
        id: 1,
        name: 'Floral Summer Dress',
        price: 1499,
        originalPrice: 1999,
        discount: 25,
        image: 'assets/homePage_img/trend1.jpg',
        category: 'Summer Wear',
        rating: 4.5,
        reviewCount: 128,
        sizes: ['S', 'M', 'L'],
        isNew: true
      },
      {
        id: 2,
        name: 'Wool Winter Coat',
        price: 2999,
        originalPrice: 3999,
        discount: 25,
        image: 'assets/homePage_img/trend2.jpg',
        category: 'Winter Wear',
        rating: 4.7,
        reviewCount: 89,
        sizes: ['M', 'L', 'XL'],
        isNew: false
      },
      {
        id: 3,
        name: 'Formal Business Suit',
        price: 4999,
        image: 'assets/homePage_img/trend3.jpg',
        category: 'Formal Wear',
        rating: 4.3,
        reviewCount: 156,
        sizes: ['38', '40', '42'],
        isNew: true
      },
      {
        id: 4,
        name: 'Casual Denim Jacket',
        price: 1999,
        originalPrice: 2499,
        discount: 20,
        image: 'assets/homePage_img/trend4.jpg',
        category: 'Casual Wear',
        rating: 4.6,
        reviewCount: 234,
        sizes: ['S', 'M', 'L', 'XL'],
        isNew: false
      },
      {
        id: 5,
        name: 'Traditional Silk Saree',
        price: 3999,
        originalPrice: 4999,
        discount: 20,
        image: 'assets/homePage_img/trend5.jpg',
        category: 'Traditional Wear',
        rating: 4.8,
        reviewCount: 178,
        sizes: ['One Size'],
        isNew: true
      },
      {
        id: 6,
        name: 'Kids Winter Jacket',
        price: 1299,
        originalPrice: 1699,
        discount: 24,
        image: 'assets/homePage_img/trend6.jpg',
        category: 'Winter Wear',
        rating: 4.4,
        reviewCount: 67,
        sizes: ['S', 'M', 'L'],
        isNew: true
      },
      {
        id: 7,
        name: 'Summer T-Shirt',
        price: 799,
        originalPrice: 999,
        discount: 20,
        image: 'assets/homePage_img/trend7.jpg',
        category: 'Summer Wear',
        rating: 4.2,
        reviewCount: 189,
        sizes: ['M', 'L', 'XL'],
        isNew: false
      },
      {
        id: 8,
        name: 'Formal Leather Shoes',
        price: 2499,
        originalPrice: 2999,
        discount: 17,
        image: 'assets/homePage_img/trend8.jpg',
        category: 'Formal Wear',
        rating: 4.5,
        reviewCount: 123,
        sizes: ['8', '9', '10'],
        isNew: true
      }
    ];
  }

  /**
   * Start automatic carousel sliding
   */
  startCarouselAutoSlide(): void {
    this.carouselInterval = setInterval(() => {
      this.nextSlide();
    }, 5000); // Change slide every 5 seconds
  }

  /**
   * Stop automatic carousel sliding
   */
  stopCarouselAutoSlide(): void {
    if (this.carouselInterval) {
      clearInterval(this.carouselInterval);
    }
  }

  /**
   * Navigate to next slide
   */
  nextSlide(): void {
    if (this.trendingProducts.length > 0) {
      this.currentSlide = (this.currentSlide + 1) % this.trendingProducts.length;
    }
  }

  /**
   * Navigate to previous slide
   */
  prevSlide(): void {
    if (this.trendingProducts.length > 0) {
      this.currentSlide = this.currentSlide === 0 ? 
        this.trendingProducts.length - 1 : 
        this.currentSlide - 1;
    }
  }

  /**
   * Go to specific slide
   */
  goToSlide(index: number): void {
    if (index >= 0 && index < this.trendingProducts.length) {
      this.currentSlide = index;
      // Reset auto-slide timer when user manually selects a slide
      this.stopCarouselAutoSlide();
      this.startCarouselAutoSlide();
    }
  }

  /**
   * Get display name for category
   */
  getCategoryDisplayName(category: string): string {
    const categoryMap: { [key: string]: string } = {
      'SUMMER': 'Summer Wear',
      'WINTER': 'Winter Wear',
      'FORMAL': 'Formal Wear',
      'CASUAL': 'Casual Wear',
      'TRADITIONAL': 'Traditional Wear'
    };
    
    return categoryMap[category] || category;
  }

  /**
   * Get display name for gender
   */
  getGenderDisplayName(gender: string): string {
    const genderMap: { [key: string]: string } = {
      'WOMEN': 'Women',
      'MEN': 'Men',
      'KIDS': 'Kids',
      'UNISEX': 'Unisex'
    };
    
    return genderMap[gender] || gender;
  }

  /**
   * Calculate discount percentage
   */
  calculateDiscountPercentage(originalPrice: number, currentPrice: number): number {
    if (!originalPrice || originalPrice <= currentPrice) return 0;
    return Math.round(((originalPrice - currentPrice) / originalPrice) * 100);
  }

  /**
   * Handle wishlist button click
   */
  onWishlistClick(product: HomeProduct, event: Event): void {
    event.stopPropagation();
    event.preventDefault();
    console.log('Added to wishlist:', product);
    // TODO: Implement wishlist service
    // this.wishlistService.addToWishlist(product.id);
    alert(`${product.name} added to wishlist!`);
  }

  /**
   * Handle quick view button click
   */
  onQuickViewClick(product: HomeProduct, event: Event): void {
    event.stopPropagation();
    event.preventDefault();
    console.log('Quick view:', product);
    // TODO: Implement quick view modal
    // this.modalService.openQuickView(product);
  }

  /**
   * Handle newsletter subscription
   */
  onNewsletterSubmit(email: string): void {
    if (!this.isValidEmail(email)) {
      alert('Please enter a valid email address');
      return;
    }
    
    console.log('Newsletter subscription:', email);
    // TODO: Implement newsletter subscription service
    // this.newsletterService.subscribe(email).subscribe(...);
    alert('Thank you for subscribing to our newsletter!');
  }

  /**
   * Validate email format
   */
  private isValidEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  /**
   * Get product rating stars
   */
  getRatingStars(rating: number = 0): number[] {
    const stars = [];
    for (let i = 1; i <= 5; i++) {
      stars.push(i <= rating ? 1 : 0);
    }
    return stars;
  }

  /**
   * Format price with Indian Rupee symbol
   */
  formatPrice(price: number): string {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0
    }).format(price);
  }

  /**
   * Handle carousel mouse enter
   */
  onCarouselMouseEnter(): void {
    this.stopCarouselAutoSlide();
  }

  /**
   * Handle carousel mouse leave
   */
  onCarouselMouseLeave(): void {
    this.startCarouselAutoSlide();
  }

  /**
   * Get limited sizes for display
   */
  getLimitedSizes(sizes: string[] = [], limit: number = 3): string[] {
    return sizes.slice(0, limit);
  }

  /**
   * Check if there are more sizes than the limit
   */
  hasMoreSizes(sizes: string[] = [], limit: number = 3): boolean {
    return sizes.length > limit;
  }

  /**
   * Get remaining sizes count
   */
  getRemainingSizesCount(sizes: string[] = [], limit: number = 3): number {
    return Math.max(0, sizes.length - limit);
  }
}