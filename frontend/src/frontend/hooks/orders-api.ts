import { apiClient } from './api-client';
import type { OrderEntity } from '../../types/OrderEntity';
import type { OrderLineEntity } from '../../types/OrderLineEntity';
import type { ProductEntity } from '../../types/ProductEntity';
import type { Page } from '../../types/Page';

export function listOrders(params?: { orderStatus?: string; page?: number; size?: number; sort?: string }): Promise<Page<OrderEntity>> {
    const query = new URLSearchParams();
    if (params?.orderStatus) query.set('orderStatus', params.orderStatus);
    if (params?.page !== undefined) query.set('page', String(params.page));
    if (params?.size !== undefined) query.set('size', String(params.size));
    if (params?.sort) query.set('sort', params.sort);
    const qs = query.toString();
    return apiClient.get<Page<OrderEntity>>(`/api/v1/orders${qs ? `?${qs}` : ''}`);
}

export function getOrderDetails(id: number): Promise<OrderEntity> {
    return apiClient.get<OrderEntity>(`/api/v1/orders/${id}`);
}

export function createOrder(data: Omit<OrderEntity, 'id'>): Promise<OrderEntity> {
    return apiClient.post<OrderEntity>(`/api/v1/orders`, data);
}

export function updateOrder(id: number, data: OrderEntity): Promise<OrderEntity> {
    return apiClient.put<OrderEntity>(`/api/v1/orders/${id}`, data);
}

export function listLineItems(orderId: number): Promise<OrderLineEntity[]> {
    return apiClient.get<OrderLineEntity[]>(`/api/v1/orders/${orderId}/items`);
}

export function addLineItem(orderId: number, data: Omit<OrderLineEntity, 'id'>): Promise<OrderLineEntity> {
    return apiClient.post<OrderLineEntity>(`/api/v1/orders/${orderId}/items`, data);
}

export function deleteLineItem(orderId: number, itemId: number): Promise<void> {
    return apiClient.delete<void>(`/api/v1/orders/${orderId}/items/${itemId}`);
}

export function listProducts(): Promise<ProductEntity[]> {
    return apiClient.get<ProductEntity[]>('/api/v1/products');
}
