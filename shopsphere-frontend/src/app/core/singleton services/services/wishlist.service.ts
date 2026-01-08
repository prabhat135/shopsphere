import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  originalPrice?: number;
  discount?: number;
  category: string;
  gender: string;
  sizes: string[];
  colors: string[];
  images: string[];
  rating: number;
  reviewCount: number;
  stockQuantity: number;
  featured: boolean;
  isNew: boolean;
  subCategory?: string;
}

export interface WishlistProduct {
  product: Product;
  addedAt: string;
}

export interface WishlistResponse {
  userId: number;
  products: WishlistProduct[];
  itemCount: number;
}

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  private baseUrl = 'http://localhost:8082/api/wishlist';

  constructor(private http: HttpClient) {}

  getWishlist(): Observable<ApiResponse<WishlistResponse>> {
    return this.http.get<ApiResponse<WishlistResponse>>(this.baseUrl);
  }

  addToWishlist(productId: number): Observable<ApiResponse<any>> {
    return this.http.post<ApiResponse<any>>(`${this.baseUrl}/add/${productId}`, {});
  }

  removeFromWishlist(productId: number): Observable<ApiResponse<any>> {
    return this.http.delete<ApiResponse<any>>(`${this.baseUrl}/remove/${productId}`);
  }

  clearWishlist(): Observable<ApiResponse<any>> {
    return this.http.delete<ApiResponse<any>>(`${this.baseUrl}/clear`);
  }

  checkProductInWishlist(productId: number): Observable<ApiResponse<{inWishlist: boolean}>> {
    return this.http.get<ApiResponse<{inWishlist: boolean}>>(`${this.baseUrl}/check/${productId}`);
  }

  getWishlistCount(): Observable<ApiResponse<{count: number}>> {
    return this.http.get<ApiResponse<{count: number}>>(`${this.baseUrl}/count`);
  }
}