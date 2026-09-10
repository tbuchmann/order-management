import { z } from 'zod';

export const ProductEntitySchema = z.object({
    id: z.number(),
    productName: z.string(),
    productCategory: z.string(),
});

export type ProductEntity = z.infer<typeof ProductEntitySchema>;
