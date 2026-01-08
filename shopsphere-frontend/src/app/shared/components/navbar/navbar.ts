import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CartService } from '../../../core/singleton-services/cart.service';
import { AuthService } from '../../../auth/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css']
})
export class Navbar implements OnInit {
  isAuthenticated = false;
  currentUser: any = null;
  searchQuery: string = '';
  showDropdown = false;
  cartItemCount = 0;

  constructor(
    private authService: AuthService,
    private cartService: CartService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authService.currentUser$.subscribe(user => {
      this.isAuthenticated = !!user;
      this.currentUser = user;
    });

    this.cartService.cart$.subscribe(cart => {
      this.cartItemCount = cart.items.reduce((sum, item) => sum + item.quantity, 0);
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
    this.showDropdown = false;
  }

  onSearch(): void {
    if (this.searchQuery.trim()) {
      this.router.navigate(['/products'], {
        queryParams: { search: this.searchQuery.trim() }
      });
      this.searchQuery = '';
    }
  }

  toggleDropdown(): void {
    this.showDropdown = !this.showDropdown;
  }

  closeDropdown(): void {
    this.showDropdown = false;
  }

  navigateToProfile(): void {
    if (this.isAuthenticated) {
      this.router.navigate(['/profile']);
    } else {
      this.router.navigate(['/register']);
    }
    this.showDropdown = false;
  }
}
