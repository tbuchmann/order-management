import { z } from 'zod';

export const PriceListEntryEntitySchema = z.object({
    id: z.number(),
    price: z.number(),
    currency: z.string(),
    productEntity: z.any().optional(),
});

export type PriceListEntryEntity = z.infer<typeof PriceListEntryEntitySchema>;
