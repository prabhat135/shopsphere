import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-terms',
  imports: [CommonModule, RouterModule],
  templateUrl: './terms.html',
  styleUrl: './terms.css',
})
export class Terms {
  hasAgreed: boolean = false;

  toggleAgreement(event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    this.hasAgreed = checkbox.checked;
    
    if (this.hasAgreed) {
      console.log('User agreed to Terms of Use');
      // You could save this preference to local storage or send to backend
      localStorage.setItem('agreedToTerms', 'true');
    }
  }

  printPage(): void {
    window.print();
  }

  ngOnInit(): void {
    // Check if user previously agreed
    const agreed = localStorage.getItem('agreedToTerms');
    if (agreed === 'true') {
      this.hasAgreed = true;
    }
  }
}
