// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
<<<<<<< HEAD
import { ProductsComponent } from './pages/products/products-list/products.component';
import { ProductDetailComponent } from './pages/products/product-detail/product-detail.component';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'products', component: ProductsComponent },
  { path: 'product/:id', component: ProductDetailComponent },
  { path: '**', redirectTo: '' } // Wildcard route
=======
import { Profile } from './pages/profile/profile';
import { Help } from './shared/components/help/help';
import { Privacy } from './shared/components/privacy/privacy';
import { Terms } from './shared/components/terms/terms';

export const routes: Routes = [
  { path: '', component: Home },
  { path:'profile', component: Profile},
  {path:'help', component: Help},
  {path:'privacy', component: Privacy},
  {path:'terms', component: Terms},
  { path: '**', redirectTo: '' }
>>>>>>> e9d9cb72b3f42baaba3eee230f7293631fbafec2
];