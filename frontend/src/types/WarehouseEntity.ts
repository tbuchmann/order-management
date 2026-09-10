import type { WarehouseItemEntity } from './WarehouseItemEntity';

export interface WarehouseEntity {
    id: number;
    warehouseItemEntityList?: WarehouseItemEntity[];
}
