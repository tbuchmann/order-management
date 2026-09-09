import { z } from 'zod';

export const WarehouseEntitySchema = z.object({
    id: z.number(),
    warehouseItemEntityList: z.array(z.any()).optional(),
});

export type WarehouseEntity = z.infer<typeof WarehouseEntitySchema>;
