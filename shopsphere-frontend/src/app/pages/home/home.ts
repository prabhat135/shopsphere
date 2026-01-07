import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Navbar } from '../../shared/components/navbar/navbar';
import { Footer } from '../../shared/components/footer/footer';

@Component({
  selector: 'app-home',
  imports: [CommonModule, Navbar, Footer],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  categories = ['Traditional', 'Western', 'Casual', 'Formals'];
  seasons = ['Summer', 'Winter', 'Festive'];

  products = [
    {
      name: 'Floral Kurti',
      price: 1499,
      image: 'https://picsum.photos/seed/p1/300/300'
    },
    {
      name: 'Denim Jacket',
      price: 2499,
      image: 'https://picsum.photos/seed/p2/300/300'
    }
  ];
}
