export interface OrderLineEntity {
    lineNumber: number;
    quantity: number;
    unit: string;
    unitPrice: number;
    currency: string;
    orderEntity?: OrderEntity;
    productEntity?: ProductEntity;
}
