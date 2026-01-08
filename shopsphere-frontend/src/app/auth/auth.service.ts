import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

// Match your backend AuthResponse.java
export interface AuthResponse {
  token: string;
  type: string;
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  role: string;
}

export interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8082/api/auth';
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadUserFromStorage();
  }

  private loadUserFromStorage(): void {
    const token = localStorage.getItem('token');
    const userStr = localStorage.getItem('user');
    
    if (token && userStr) {
      const user = JSON.parse(userStr);
      this.currentUserSubject.next(user);
    }
  }

  // Login - matches POST /api/auth/login
  login(email: string, password: string): Observable<AuthResponse> {
    const body = { email, password };
    return this.http.post<AuthResponse>(`${this.baseUrl}/login`, body)
      .pipe(
        tap(response => {
          this.setSession(response);
        })
      );
  }

  // Register - matches POST /api/auth/register
  register(email: string, password: string): Observable<AuthResponse> {
    const body = { email, password };
    return this.http.post<AuthResponse>(`${this.baseUrl}/register`, body)
      .pipe(
        tap(response => {
          this.setSession(response);
        })
      );
  }

  private setSession(authData: AuthResponse): void {
    // Store JWT token
    localStorage.setItem('token', authData.token);
    
    // Create user object from response
    const user: User = {
      id: authData.id,
      email: authData.email,
      firstName: authData.firstName,
      lastName: authData.lastName,
      role: authData.role
    };
    
    // Store user info
    localStorage.setItem('user', JSON.stringify(user));
    
    // Update observable
    this.currentUserSubject.next(user);
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.currentUserSubject.next(null);
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  // Optional: Refresh token if needed
  refreshToken(): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/refresh-token`, {
      token: this.getToken()
    });
  }

  // Optional: Get user profile
  getProfile(): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/profile`);
  }

  // Optional: Update user profile
  updateProfile(userData: Partial<User>): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/profile`, userData);
  }
}