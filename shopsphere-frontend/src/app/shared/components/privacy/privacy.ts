import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-privacy',
  imports: [RouterModule, CommonModule],
  templateUrl: './privacy.html',
  styleUrl: './privacy.css',
})
export class Privacy {
  printPage(): void {
    window.print();
  }
}
