import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-help',
  imports: [RouterModule, CommonModule],
  templateUrl: './help.html',
  styleUrl: './help.css',
})
export class Help {
  activeFaqIndex: number | null = null;

  toggleFaq(index: number): void {
    if (this.activeFaqIndex === index) {
      this.activeFaqIndex = null;
    } else {
      this.activeFaqIndex = index;
    }
  }

  isFaqActive(index: number): boolean {
    return this.activeFaqIndex === index;
  }
}
