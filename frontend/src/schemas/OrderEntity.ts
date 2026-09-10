import { z } from 'zod';

export const OrderEntitySchema = z.object({
    id: z.number().optional(),
    orderDate: z.string(),
    desiredShippingDate: z.string(),
    orderStatus: z.string(),
    orderValue: z.number(),
    customerEntity: z.any().optional(),
    deliveryAddressEntity: z.any().optional(),
    orderLineEntityList: z.array(z.any()).optional(),
});

export type OrderEntity = z.infer<typeof OrderEntitySchema>;
