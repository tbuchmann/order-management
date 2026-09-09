export interface PriceListEntryEntity {
    pleId: number;
    price: number;
    currency: string;
    productEntity?: ProductEntity;
}
