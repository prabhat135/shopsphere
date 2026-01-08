import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../core/singleton-services/services/product.service';
import { WishlistService } from '../../core/singleton-services/services/wishlist.service';
import { ApiResponse } from '../../shared/models/product.model';
import { Navbar } from '../../shared/components/navbar/navbar';
import { Footer } from '../../shared/components/footer/footer';

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
  wishlistStatus: { [key: number]: boolean } = {};

  constructor(
    private productService: ProductService,
    private wishlistService: WishlistService
  ) {}

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
            imageUrl: product.images?.[0] || 'default-product.jpg',
            name: product.name,
            price: product.price,
            category: product.category
          }));
        } else {
          this.loadMockTrendingProducts();
        }
        this.isLoading = false;
      },
      error: (error: any) => {
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
            image: product.images?.[0] || 'default-product.jpg',
            category: this.getCategoryDisplayName(product.category),
            rating: product.rating,
            reviewCount: product.reviewCount,
            sizes: product.sizes,
            isNew: product.isNew
          }));
          this.checkWishlistStatus();
        } else {
          this.loadMockProducts();
          this.checkWishlistStatus();
        }
      },
      error: (error: any) => {
        console.error('Error loading featured products:', error);
        this.loadMockProducts();
        this.checkWishlistStatus();
      }
    });
  }

  /**
   * Check wishlist status for products
   */
  checkWishlistStatus(): void {
    this.products.forEach(product => {
      this.wishlistService.checkProductInWishlist(product.id).subscribe({
        next: (response) => {
          if (response.success) {
            this.wishlistStatus[product.id] = response.data.inWishlist;
          }
        },
        error: (error) => {
          console.error('Error checking wishlist status:', error);
        }
      });
    });
  }

  /**
   * Handle wishlist button click
   */
  onWishlistClick(product: HomeProduct, event: Event): void {
    event.stopPropagation();
    event.preventDefault();
    
    const productId = product.id;
    
    if (this.wishlistStatus[productId]) {
      this.wishlistService.removeFromWishlist(productId).subscribe({
        next: (response) => {
          if (response.success) {
            this.wishlistStatus[productId] = false;
          }
        },
        error: (error) => {
          console.error('Error removing from wishlist:', error);
        }
      });
    } else {
      this.wishlistService.addToWishlist(productId).subscribe({
        next: (response) => {
          if (response.success) {
            this.wishlistStatus[productId] = true;
          }
        },
        error: (error) => {
          console.error('Error adding to wishlist:', error);
        }
      });
    }
  }

  /**
   * Load mock trending products as fallback
   */
  private loadMockTrendingProducts(): void {
    this.trendingProducts = [
      { 
        productId: 1, 
        imageUrl: 'homePage_img/trend1.jpg', 
        name: 'Floral Summer Dress',
        price: 1499
      },
      { 
        productId: 2, 
        imageUrl: 'homePage_img/trend2.jpg', 
        name: 'Wool Winter Coat',
        price: 2999
      },
      { 
        productId: 3, 
        imageUrl: 'homePage_img/trend3.jpg', 
        name: 'Formal Business Suit',
        price: 4999
      },
      { 
        productId: 4, 
        imageUrl: 'homePage_img/trend4.jpg', 
        name: 'Casual Denim Jacket',
        price: 1999
      },
      { 
        productId: 5, 
        imageUrl: 'homePage_img/trend5.jpg', 
        name: 'Traditional Silk Saree',
        price: 3999
      },
      { 
        productId: 6, 
        imageUrl: 'homePage_img/trend6.jpg', 
        name: 'Kids Winter Jacket',
        price: 1299
      },
      { 
        productId: 7, 
        imageUrl: 'homePage_img/trend7.jpg', 
        name: 'Summer T-Shirt',
        price: 799
      },
      { 
        productId: 8, 
        imageUrl: 'homePage_img/trend8.jpg', 
        name: 'Formal Leather Shoes',
        price: 2499
      },
      { 
        productId: 9, 
        imageUrl: 'homePage_img/trend9.jpg', 
        name: 'Evening Gown',
        price: 3499
      },
      { 
        productId: 10, 
        imageUrl: 'homePage_img/trend10.jpg', 
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
        image: 'homePage_img/trend1.jpg',
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
        image: 'homePage_img/trend2.jpg',
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
        image: 'homePage_img/trend3.jpg',
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
        image: 'homePage_img/trend4.jpg',
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
        image: 'homePage_img/trend5.jpg',
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
        image: 'homePage_img/trend6.jpg',
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
        image: 'homePage_img/trend7.jpg',
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
        image: 'homePage_img/trend8.jpg',
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
    }, 5000);
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
   * Handle quick view button click
   */
  onQuickViewClick(product: HomeProduct, event: Event): void {
    event.stopPropagation();
    event.preventDefault();
  }

  /**
   * Handle newsletter subscription
   */
  onNewsletterSubmit(email: string): void {
    if (!this.isValidEmail(email)) {
      return;
    }
  }

  /**
   * Validate email format
   */
  private isValidEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }
}
