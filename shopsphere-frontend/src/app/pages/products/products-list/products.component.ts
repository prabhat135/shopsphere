import {Component,OnInit} from '@angular/core';
import { ProductService } from '../../../core/singleton services/services/product.service';
import { Product } from '../../../shared/models/product.model';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: Product[] = [];
  selectedStyle = 'TRADITIONAL';

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts('TRADITIONAL', 'MEN');
  }

  loadProducts(style: string, gender: string) {
    this.selectedStyle = style;
    this.productService
      .getProducts(style, gender)
      .subscribe((res: Product[]) => this.products = res);
  }
}
