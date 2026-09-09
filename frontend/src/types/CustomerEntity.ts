export interface CustomerEntity {
    customerId: number;
    companyName: string;
    phoneNumber: string;
    companyEmail: string;
    dunsNumber: number;
    deliveryAddressEntity?: DeliveryAddressEntity;
}
