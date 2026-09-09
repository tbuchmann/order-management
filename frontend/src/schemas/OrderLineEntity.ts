import { z } from 'zod';

export const OrderLineEntitySchema = z.object({
    lineNumber: z.number(),
    quantity: z.number(),
    unit: z.string(),
    unitPrice: z.number(),
    currency: z.string(),
    orderEntity: z.any().optional(),
    productEntity: z.any().optional(),
});

export type OrderLineEntity = z.infer<typeof OrderLineEntitySchema>;
