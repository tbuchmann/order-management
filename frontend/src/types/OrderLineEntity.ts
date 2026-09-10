import type { OrderEntity } from './OrderEntity';
import type { ProductEntity } from './ProductEntity';

export interface OrderLineEntity {
    id: number;
    quantity: number;
    unit: string;
    unitPrice: number;
    currency: string;
    productId?: number;
    productName?: string;
    orderEntity?: OrderEntity;
    productEntity?: ProductEntity;
}
