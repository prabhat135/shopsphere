import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
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
];