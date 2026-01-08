import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, ParamMap, Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../../core/singleton-services/services/product.service.js';
import { Product, ApiResponse } from '../../../shared/models/product.model';
import { Navbar } from '../../../shared/components/navbar/navbar';
import { Footer } from '../../../shared/components/footer/footer';

@Component({
  selector: 'app-product-detail',
  standalone: true,
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
    this.route.paramMap.subscribe((params: ParamMap) => {
      const id = params.get('id');
      if (id) {
        this.loadProduct(+id);
      }
    });
  }

  loadProduct(id: number): void {
    this.isLoading = true;
    this.productService.getProductById(id).subscribe({
      next: (response: ApiResponse<Product>) => {
        if (response.success && response.data) {
          this.product = response.data;

          if (this.product.sizes.length > 0) {
            this.selectedSize = this.product.sizes[0];
          }
          if (this.product.colors.length > 0) {
            this.selectedColor = this.product.colors[0];
          }

          this.loadRelatedProducts();
        }
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error loading product:', error);
        this.isLoading = false;
      }
    });
  }

  loadRelatedProducts(): void {
    if (!this.product) return;

    const filterOptions = {
      categories: [this.product.category],
      genders: [this.product.gender],
      priceRange: { min: 0, max: 10000 },
      sizes: [],
      colors: [],
      sortBy: 'popular' as const
    };

    this.productService.filterProducts(filterOptions).subscribe({
      next: (response: ApiResponse<Product[]>) => {
        if (response.success && response.data) {
          this.relatedProducts = response.data
            .filter((p: Product) => p.id !== this.product!.id)
            .slice(0, 4);
        }
      },
      error: (error: any) => {
        console.error('Error loading related products:', error);
      }
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

    console.log('Added to cart:', {
      product: this.product,
      size: this.selectedSize,
      color: this.selectedColor,
      quantity: this.quantity
    });

    alert('Product added to cart!');
  }

  addToWishlist(): void {
    if (!this.product) return;
    console.log('Added to wishlist:', this.product);
    alert('Product added to wishlist!');
  }

  buyNow(): void {
    if (!this.product || !this.selectedSize || !this.selectedColor) {
      alert('Please select size and color');
      return;
    }

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
    return this.product ? this.product.price : 0;
  }

  getDiscountPercentage(): number {
    if (!this.product?.originalPrice) return 0;
    return Math.round(
      ((this.product.originalPrice - this.product.price) / this.product.originalPrice) * 100
    );
  }

  getCategoryName(category: string): string {
    const names: Record<string, string> = {
      SUMMER: 'Summer Wear',
      WINTER: 'Winter Wear',
      FORMAL: 'Formal Wear',
      CASUAL: 'Casual Wear',
      TRADITIONAL: 'Traditional Wear'
    };
    return names[category] || category;
  }

  getGenderName(gender: string): string {
    const names: Record<string, string> = {
      WOMEN: 'Women',
      MEN: 'Men',
      KIDS: 'Kids',
      UNISEX: 'Unisex'
    };
    return names[gender] || gender;
  }
}
