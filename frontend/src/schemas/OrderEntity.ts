import { z } from 'zod';

export const OrderEntitySchema = z.object({
    orderId: z.number(),
    orderDate: z.string().datetime(),
    desiredShippingDate: z.string().datetime(),
    orderStatus: z.string(),
    orderValue: z.number(),
    customerEntity: z.any().optional(),
    deliveryAddressEntity: z.any().optional(),
    deliveryAddressEntity: z.any().optional(),
    orderLineEntityList: z.array(z.any()).optional(),
});

export type OrderEntity = z.infer<typeof OrderEntitySchema>;
