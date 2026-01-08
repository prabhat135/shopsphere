import { Routes } from '@angular/router';
import { ProductsComponent } from './pages/products/products-list/products.component';
import { ProductDetailComponent } from './pages/products/product-detail/product-detail.component';
import { Profile } from './pages/profile/profile';
import { Help } from './shared/components/help/help';
import { Privacy } from './shared/components/privacy/privacy';
import { Terms } from './shared/components/terms/terms';
import { Home } from './pages/home/home';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';


export const routes: Routes = [
  { path: '', component: Home },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'products', component: ProductsComponent },
  { path: 'product/:id', component: ProductDetailComponent },
  { path: 'profile', component: Profile },
  { path: 'help', component: Help },
  { path: 'privacy', component: Privacy },
  { path: 'terms', component: Terms },
  { path: '**', redirectTo: '' }
];