export interface OrderEntity {
    orderId: number;
    orderDate: string;
    desiredShippingDate: string;
    orderStatus: string;
    orderValue: number;
    customerEntity?: CustomerEntity;
    deliveryAddressEntity?: DeliveryAddressEntity;
    deliveryAddressEntity?: DeliveryAddressEntity;
    orderLineEntityList?: OrderLineEntity[];
}
