import type { ProductEntity } from './ProductEntity';

export interface PriceListEntryEntity {
    id: number;
    price: number;
    currency: string;
    productEntity?: ProductEntity;
}
