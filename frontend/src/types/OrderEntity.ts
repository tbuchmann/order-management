import type { CustomerEntity } from './CustomerEntity';
import type { DeliveryAddressEntity } from './DeliveryAddressEntity';
import type { OrderLineEntity } from './OrderLineEntity';

export interface OrderEntity {
    id: number;
    orderDate: string;
    desiredShippingDate: string;
    orderStatus: string;
    orderValue: number;
    customerEntity?: CustomerEntity;
    deliveryAddressEntity?: DeliveryAddressEntity;
    orderLineEntityList?: OrderLineEntity[];
}
