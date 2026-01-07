import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Navbar } from '../../shared/components/navbar/navbar';
import { Footer } from '../../shared/components/footer/footer';

@Component({
  selector: 'app-home',
  imports: [CommonModule, RouterModule, Navbar, Footer],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  currentSlide = 0;

  trendingProducts = [
    { productId: 1, imageUrl: 'homePage_img/trend1.jpg' },
    { productId: 2, imageUrl: 'homePage_img/trend2.jpg' },
    { productId: 3, imageUrl: 'homePage_img/trend3.jpg' },
    { productId: 4, imageUrl: 'homePage_img/trend4.jpg' },
    { productId: 5, imageUrl: 'homePage_img/trend5.jpg' },
    { productId: 6, imageUrl: 'homePage_img/trend6.jpg' },
    { productId: 7, imageUrl: 'homePage_img/trend7.jpg' },
    { productId: 8, imageUrl: 'homePage_img/trend8.jpg' },
    { productId: 9, imageUrl: 'homePage_img/trend9.jpg' },
    { productId: 10, imageUrl: 'homePage_img/trend10.jpg' }
  ];

  products = [
    {
      name: 'Womens Black Shirt',
      price: 1499,
      image: 'homePage_img/shirt.jpg'
    },
    {
      name: 'Denim Jacket',
      price: 2499,
      image: 'homePage_img/denim.jpg'
    },
    {
      name: 'Skirt for Women',
      price: 899,
      image: 'homePage_img/skirt.jpg'
    },
    {
      name: 'Long Coat for Men',
      price: 2999,
      image: 'homePage_img/longcoat.jpg'
    },
    {
      name: 'Kids Skirt',
      price: 1299,
      image: 'homePage_img/kidskirt.jpg'
    },
    {
      name: 'Men Chikankari Kurta',
      price: 1899,
      image: 'homePage_img/kurta.jpg'
    }
  ];

  nextSlide() {
    this.currentSlide = (this.currentSlide + 1) % this.trendingProducts.length;
  }

  prevSlide() {
    this.currentSlide = this.currentSlide === 0 ? 
      this.trendingProducts.length - 1 : 
      this.currentSlide - 1;
  }

  goToSlide(index: number) {
    this.currentSlide = index;
  }

  // Optional: Auto-rotate carousel
  ngOnInit() {
    setInterval(() => {
      this.nextSlide();
    }, 5000);
  }
}