import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './signup.component.html'
})
export class SignupComponent {

  name = '';
  email = '';
  password = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  signup() {
    this.authService.signup({
      name: this.name,
      email: this.email,
      password: this.password
    }).subscribe(() => {
      this.router.navigate(['/login']);
    });
  }
}
