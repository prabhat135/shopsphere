export interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
    imageUrl: string;
    styleCategory: 'TRADITIONAL' | 'WESTERN' | 'SEASONAL';
    genderCategory: 'MEN' | 'WOMEN' | 'KIDS';
    stock: number;
  }
  