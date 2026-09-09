import { z } from 'zod';

export const WarehouseItemEntitySchema = z.object({
    id: z.number(),
    currentStock: z.number(),
    unit: z.string(),
    minimumStockLevel: z.number(),
    productEntity: z.any().optional(),
});

export type WarehouseItemEntity = z.infer<typeof WarehouseItemEntitySchema>;
