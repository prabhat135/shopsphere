import { Product } from './product.model';

// Matches CartDTO from backend
export interface Cart {
  id: number;
  totalPrice: number;
  items: CartItem[];
  createdAt?: string;
  updatedAt?: string;
}

// Matches CartItemDTO from backend
export interface CartItem {
  id: number;
  product: Product;
  quantity: number;
  selectedSize: string;
  selectedColor: string;
  price: number;
}

// Request for adding item to cart - matches CartService.addToCart() parameters
export interface CartItemRequest {
  productId: number;
  quantity: number;
  selectedSize: string;
  selectedColor: string;
}

// Request for updating cart item quantity
export interface UpdateCartItemRequest {
  quantity: number;
}

// Response from cart APIs
export interface CartResponse {
  success: boolean;
  message: string;
  data: Cart;
}

// Response for cart count
export interface CartCountResponse {
  count: number;
}

// Response for cart total
export interface CartTotalResponse {
  total: number;
}

// Response for in-cart check
export interface InCartResponse {
  inCart: boolean;
}

// Request for merging carts (for guest users)
export interface MergeCartRequest {
  items: CartItem[];
}

// Stock validation response
export interface StockValidationResponse {
  validated: boolean;
  message: string;
  cart: Cart;
}