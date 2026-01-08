import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import { 
  Cart, 
  CartItem, 
  CartItemRequest, 
  UpdateCartItemRequest 
} from '../../shared/models/cart.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private baseUrl = `${environment.apiUrl}/api/cart`;
  private apiUrl = `${this.baseUrl}/public`; // For public endpoint
  
  private cartSubject = new BehaviorSubject<Cart>({
    id: 0,
    totalPrice: 0,
    items: []
  });
  
  cart$ = this.cartSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadCart();
  }

  // GET /api/cart/public - Get test cart
  getCart(): Observable<Cart> {
    return this.http.get<Cart>(this.apiUrl).pipe(
      tap(cart => {
        this.cartSubject.next(cart);
        this.updateCartItemCount(cart);
      })
    );
  }

  // POST /api/cart/add - Add item to cart
  addToCart(itemRequest: CartItemRequest): Observable<Cart> {
    // Based on controller: addToCart(productId, quantity, size, color)
    return this.http.post<Cart>(`${this.baseUrl}/add`, null, {
      params: {
        productId: itemRequest.productId.toString(),
        quantity: itemRequest.quantity.toString(),
        size: itemRequest.selectedSize,
        color: itemRequest.selectedColor
      }
    }).pipe(
      tap(cart => {
        this.cartSubject.next(cart);
        this.updateCartItemCount(cart);
      })
    );
  }

  // PUT /api/cart/update/{itemId} - Update quantity
  updateCartItem(itemId: number, updateRequest: UpdateCartItemRequest): Observable<Cart> {
    return this.http.put<Cart>(`${this.baseUrl}/update/${itemId}`, null, {
      params: {
        quantity: updateRequest.quantity.toString()
      }
    }).pipe(
      tap(cart => {
        this.cartSubject.next(cart);
        this.updateCartItemCount(cart);
      })
    );
  }

  // DELETE /api/cart/remove/{itemId} - Remove item
  removeFromCart(itemId: number): Observable<Cart> {
    return this.http.delete<Cart>(`${this.baseUrl}/remove/${itemId}`).pipe(
      tap(cart => {
        this.cartSubject.next(cart);
        this.updateCartItemCount(cart);
      })
    );
  }

  // DELETE /api/cart/clear - Clear cart
  clearCart(): Observable<Cart> {
    return this.http.delete<Cart>(`${this.baseUrl}/clear`).pipe(
      tap(cart => {
        this.cartSubject.next(cart);
        this.updateCartItemCount(cart);
      })
    );
  }

  // Get cart item count for navbar
  getCartItemCount(): number {
    const cart = this.cartSubject.value;
    return cart.items.reduce((sum, item) => sum + item.quantity, 0);
  }

  // Update cart item count
  private updateCartItemCount(cart: Cart): void {
    const totalItems = cart.items.reduce((sum, item) => sum + item.quantity, 0);
    // For navbar updates
  }

  // Load cart on init
  private loadCart(): void {
    this.getCart().subscribe({
      error: () => {
        console.log('No cart found or not logged in');
        const emptyCart: Cart = {
          id: 0,
          totalPrice: 0,
          items: []
        };
        this.cartSubject.next(emptyCart);
      }
    });
  }

  // Helper: Calculate item total
  calculateItemTotal(item: CartItem): number {
    return item.price * item.quantity;
  }
}