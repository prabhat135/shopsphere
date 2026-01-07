// src/app/components/product-detail/product-detail.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../../core/singleton services/services/product.service';
import { Product } from '../../../shared/models/product.model';
import { Navbar } from '../../../shared/components/navbar/navbar';
import { Footer } from '../../../shared/components/footer/footer';

@Component({
  selector: 'app-product-detail',
  imports: [CommonModule, RouterModule, FormsModule, Navbar, Footer],
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {
  product: Product | null = null;
  selectedImageIndex = 0;
  selectedSize: string | null = null;
  selectedColor: string | null = null;
  quantity = 1;
  relatedProducts: Product[] = [];
  isLoading = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = +params['id'];
      this.loadProduct(id);
    });
  }

  loadProduct(id: number): void {
    this.isLoading = true;
    this.productService.getProductById(id).subscribe({
      next: (product) => {
        this.product = product || null;
        if (this.product) {
          this.loadRelatedProducts();
          // Set default selections
          if (this.product.size.length > 0) {
            this.selectedSize = this.product.size[0];
          }
          if (this.product.color.length > 0) {
            this.selectedColor = this.product.color[0];
          }
        }
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading product:', error);
        this.isLoading = false;
      }
    });
  }

  loadRelatedProducts(): void {
    if (!this.product) return;
    
    this.productService.filterProducts({
      categories: [this.product.category],
      genders: [this.product.gender]
    }).subscribe(products => {
      // Exclude current product and limit to 4
      this.relatedProducts = products
        .filter(p => p.id !== this.product!.id)
        .slice(0, 4);
    });
  }

  selectImage(index: number): void {
    this.selectedImageIndex = index;
  }

  selectSize(size: string): void {
    this.selectedSize = size;
  }

  selectColor(color: string): void {
    this.selectedColor = color;
  }

  increaseQuantity(): void {
    this.quantity++;
  }

  decreaseQuantity(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  addToCart(): void {
    if (!this.product || !this.selectedSize || !this.selectedColor) {
      alert('Please select size and color');
      return;
    }

    // Implement cart logic here
    const cartItem = {
      product: this.product,
      size: this.selectedSize,
      color: this.selectedColor,
      quantity: this.quantity
    };
    
    console.log('Added to cart:', cartItem);
    alert('Product added to cart!');
  }

  addToWishlist(): void {
    if (!this.product) return;
    
    // Implement wishlist logic here
    console.log('Added to wishlist:', this.product);
    alert('Product added to wishlist!');
  }

  buyNow(): void {
    if (!this.product || !this.selectedSize || !this.selectedColor) {
      alert('Please select size and color');
      return;
    }

    // Navigate to checkout or implement buy now logic
    this.router.navigate(['/checkout'], {
      queryParams: {
        productId: this.product.id,
        size: this.selectedSize,
        color: this.selectedColor,
        quantity: this.quantity
      }
    });
  }

  getDiscountedPrice(): number {
    if (!this.product) return 0;
    
    if (this.product.originalPrice) {
      return this.product.price;
    }
    return this.product.price;
  }

  getDiscountPercentage(): number {
    if (!this.product || !this.product.originalPrice) return 0;
    
    return Math.round(((this.product.originalPrice - this.product.price) / this.product.originalPrice) * 100);
  }

  // Helper methods for display
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