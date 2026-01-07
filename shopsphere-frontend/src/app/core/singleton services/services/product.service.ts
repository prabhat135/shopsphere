import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../../../shared/models/product.model';

@Injectable({ providedIn: 'root' })
export class ProductService {

  private api = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) {}

  getProducts(style: string, gender: string) {
    return this.http.get<Product[]>(
      `${this.api}?style=${style}&gender=${gender}`
    );
  }
}
