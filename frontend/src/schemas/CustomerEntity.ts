import { z } from 'zod';

export const CustomerEntitySchema = z.object({
    customerId: z.number(),
    companyName: z.string(),
    phoneNumber: z.string(),
    companyEmail: z.string().email(),
    dunsNumber: z.number(),
    deliveryAddressEntity: z.any().optional(),
});

export type CustomerEntity = z.infer<typeof CustomerEntitySchema>;
