import type { ProductEntity } from './ProductEntity';

export interface WarehouseItemEntity {
    id: number;
    currentStock: number;
    unit: string;
    minimumStockLevel: number;
    productEntity?: ProductEntity;
}
