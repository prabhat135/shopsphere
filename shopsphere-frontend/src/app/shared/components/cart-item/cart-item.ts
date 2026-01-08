import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartItem } from '../../models/cart.model';

@Component({
  selector: 'app-cart-item',
  standalone: true,
  imports: [CommonModule], 
  templateUrl: './cart-item.html',  // Angular generated name
  styleUrls: ['./cart-item.css']    // Angular generated name
})
export class CartItemComponent {
  @Input() item!: CartItem;
  @Input() isUpdating = false;
  
  @Output() quantityChange = new EventEmitter<number>();
  @Output() remove = new EventEmitter<void>();
  @Output() moveToWishlist = new EventEmitter<void>();

  onQuantityChange(newQuantity: number): void {
    if (newQuantity >= 1 && newQuantity <= 10) {
      this.quantityChange.emit(newQuantity);
    }
  }

  onRemove(): void {
    this.remove.emit();
  }

  onMoveToWishlist(): void {
    this.moveToWishlist.emit();
  }

  calculateItemTotal(): number {
    return this.item.price * this.item.quantity;
  }

  getStockStatus(): string {
    if (this.item.product.stockQuantity === 0) return 'Out of Stock';
    if (this.item.product.stockQuantity < 10) return 'Low Stock';
    return 'In Stock';
  }

  getStockClass(): string {
    if (this.item.product.stockQuantity === 0) return 'out-of-stock';
    if (this.item.product.stockQuantity < 10) return 'low-stock';
    return 'in-stock';
  }
}