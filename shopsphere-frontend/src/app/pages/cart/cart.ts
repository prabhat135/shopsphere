import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CartService } from '../../core/singleton-services/cart.service';
import { Cart, CartItem } from '../../shared/models/cart.model';
import { CartItemComponent } from '../../shared/components/cart-item/cart-item';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, RouterModule, CartItemComponent],
  templateUrl: './cart.html',
  styleUrls: ['./cart.css'],
})
export class CartComponent implements OnInit, OnDestroy {
  cart: Cart | null = null;
  loading = true;
  updatingItems = new Set<number>();
  private cartSubscription!: Subscription;

  constructor(
    private cartService: CartService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadCart();
    this.cartSubscription = this.cartService.cart$.subscribe((cart: Cart | null) => {
      this.cart = cart;
      this.loading = false;
    });
  }

  ngOnDestroy(): void {
    if (this.cartSubscription) {
      this.cartSubscription.unsubscribe();
    }
  }

  loadCart(): void {
    this.loading = true;
    this.cartService.getCart().subscribe({
      error: (error: any) => {
        console.error('Error loading cart:', error);
        this.loading = false;
      }
    });
  }

  updateQuantity(item: CartItem, newQuantity: number): void {
    if (newQuantity < 1 || newQuantity > 10) return;
    
    this.updatingItems.add(item.id);
    
    this.cartService.updateCartItem(item.id, { quantity: newQuantity }).subscribe({
      next: () => {
        this.updatingItems.delete(item.id);
      },
      error: (error: { error: { message: any; }; }) => {
        console.error('Error updating quantity:', error);
        this.updatingItems.delete(item.id);
        alert(error.error?.message || 'Failed to update quantity');
        this.loadCart(); // Reload cart to sync with server
      }
    });
  }

  removeItem(itemId: number): void {
    if (!confirm('Are you sure you want to remove this item from your cart?')) {
      return;
    }

    this.cartService.removeFromCart(itemId).subscribe({
      error: (error: { error: { message: any; }; }) => {
        console.error('Error removing item:', error);
        alert(error.error?.message || 'Failed to remove item');
      }
    });
  }

  clearCart(): void {
    if (!this.cart || this.cart.items.length === 0) return;
    
    if (!confirm('Are you sure you want to clear your entire cart?')) {
      return;
    }

    this.cartService.clearCart().subscribe({
      error: (error: { error: { message: any; }; }) => {
        console.error('Error clearing cart:', error);
        alert(error.error?.message || 'Failed to clear cart');
      }
    });
  }

  proceedToCheckout(): void {
    //  use getCart
    this.cartService.getCart().subscribe({
      next: (cart) => {
        if (cart.items.length === 0) {
          alert('Your cart is empty. Please add items before checkout.');
          return;
        }
        this.router.navigate(['/checkout']);
      },
      error: (error) => {
        console.error('Error loading cart:', error);
        alert('Please review your cart items before checkout.');
      }
    });
  }

  continueShopping(): void {
    this.router.navigate(['/products']);
  }

  calculateItemTotal(item: CartItem): number {
    return item.price * item.quantity;
  }

  calculateSavings(): number {
    if (!this.cart) return 0;
    
    return this.cart.items.reduce((total, item) => {
      if (item.product.discountPrice) {
        return total + ((item.product.price - item.product.discountPrice) * item.quantity);
      }
      return total;
    }, 0);
  }

  get isCartEmpty(): boolean {
    return !this.cart || this.cart.items.length === 0;
  }

  get subtotal(): number {
    if (!this.cart) return 0;
    return this.cart.totalPrice;
  }

  get shipping(): number {
    // Free shipping for orders above $50
    if (this.subtotal >= 50) return 0;
    return 5.99; // Standard shipping
  }

  get tax(): number {
    if (!this.cart) return 0;
    return this.subtotal * 0.08; // 8% tax
  }

  get total(): number {
    return this.subtotal + this.shipping + this.tax;
  }
  onMoveToWishlist(item: CartItem): void {
    console.log('Move to wishlist:', item);
    // Implement wishlist service call here
  }
}
