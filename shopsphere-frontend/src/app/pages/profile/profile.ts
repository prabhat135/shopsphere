import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { Footer } from '../../shared/components/footer/footer';

@Component({
  selector: 'app-profile',
  imports: [CommonModule, RouterModule, Footer],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit {
  userName: string = 'John Doe';
  userEmail: string = 'johndoe@example.com';

  constructor(private router: Router) {}

  ngOnInit(): void {
    // In real application, fetch user data from service
    this.loadUserData();
  }

  private loadUserData(): void {
    // Fetch user data from localStorage or API
    const savedName = localStorage.getItem('userName');
    const savedEmail = localStorage.getItem('userEmail');
    
    if (savedName) this.userName = savedName;
    if (savedEmail) this.userEmail = savedEmail;
  }

  logout(): void {
    // Clear user session
    localStorage.removeItem('authToken');
    localStorage.removeItem('userName');
    localStorage.removeItem('userEmail');
    
    // Navigate to home page
    this.router.navigate(['/']);
    
    // In real application, you might want to call an API to invalidate the token
    console.log('User logged out');
  }
}
