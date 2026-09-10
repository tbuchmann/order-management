import type { DeliveryAddressEntity } from './DeliveryAddressEntity';

export interface CustomerEntity {
    id: number;
    companyName: string;
    phoneNumber: string;
    companyEmail: string;
    dunsNumber: number;
    deliveryAddressEntity?: DeliveryAddressEntity;
}
