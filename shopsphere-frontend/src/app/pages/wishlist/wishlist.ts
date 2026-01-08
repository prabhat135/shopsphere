import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WishlistService, WishlistResponse, ApiResponse} from '../../core/singleton-services/services/wishlist.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-wishlist',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './wishlist.html',
  styleUrl: './wishlist.css',
})
export class Wishlist implements OnInit {
  wishlist: WishlistResponse | null = null;
  isLoading = false;
  error: string | null = null;

  constructor(
    private wishlistService: WishlistService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadWishlist();
  }

  loadWishlist(): void {
    this.isLoading = true;
    this.error = null;
    
    this.wishlistService.getWishlist().subscribe({
      next: (response: ApiResponse<WishlistResponse>) => {
        if (response.success) {
          this.wishlist = response.data;
        } else {
          this.error = response.message;
        }
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading wishlist:', error);
        this.error = 'Failed to load wishlist';
        this.isLoading = false;
      }
    });
  }

  removeFromWishlist(productId: number): void {
    if (confirm('Remove this product from wishlist?')) {
      this.wishlistService.removeFromWishlist(productId).subscribe({
        next: (response: ApiResponse<any>) => {
          if (response.success) {
            this.loadWishlist();
          } else {
            alert(response.message);
          }
        },
        error: (error) => {
          console.error('Error removing from wishlist:', error);
          alert('Failed to remove from wishlist');
        }
      });
    }
  }

  clearWishlist(): void {
    if (confirm('Clear all items from wishlist?')) {
      this.wishlistService.clearWishlist().subscribe({
        next: (response: ApiResponse<any>) => {
          if (response.success) {
            this.wishlist = null;
          } else {
            alert(response.message);
          }
        },
        error: (error) => {
          console.error('Error clearing wishlist:', error);
          alert('Failed to clear wishlist');
        }
      });
    }
  }

  viewProduct(productId: number): void {
    this.router.navigate(['/product', productId]);
  }

  addToCart(product: any): void {
    this.router.navigate(['/product', product.product.id]);
  }

  continueShopping(): void {
    this.router.navigate(['/products']);
  }

  handleImageError(event: any): void {
    event.target.src = 'assets/default-product.jpg';
  }
}
