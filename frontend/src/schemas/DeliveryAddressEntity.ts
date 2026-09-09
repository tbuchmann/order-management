import { z } from 'zod';

export const DeliveryAddressEntitySchema = z.object({
    id: z.number(),
    street: z.string(),
    postcode: z.string(),
    city: z.string(),
    country: z.string(),
});

export type DeliveryAddressEntity = z.infer<typeof DeliveryAddressEntitySchema>;
