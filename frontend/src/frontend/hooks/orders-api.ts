import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import type { UseQueryResult, UseMutationResult } from '@tanstack/react-query';
import { apiClient } from './api-client';

export function uselistOrders(params?: { orderStatus: string }): UseQueryResult<OrderEntity> {
    return useQuery<OrderEntity>({
        queryKey: ['listOrders', ...(params ? [params] : [])],
        queryFn: () => apiClient.listOrders(params),
    });
}

export function usegetOrderDetails(params?: { id: number }): UseQueryResult<OrderEntity> {
    return useQuery<OrderEntity>({
        queryKey: ['getOrderDetails', ...(params ? [params] : [])],
        queryFn: () => apiClient.getOrderDetails(params),
    });
}

export function uselistLineItems(params?: { orderId: number }): UseQueryResult<OrderLineEntity[]> {
    return useQuery<OrderLineEntity[]>({
        queryKey: ['listLineItems', ...(params ? [params] : [])],
        queryFn: () => apiClient.listLineItems(params),
    });
}

export function usecreateOrder(): UseMutationResult<OrderEntity, Error, OrderEntity> {
    const queryClient = useQueryClient();
    return useMutation<OrderEntity, Error, OrderEntity>({
        mutationFn: (data) => apiClient.createOrder(data),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['createOrder'] });
        },
    });
}

export function useupdateOrder(): UseMutationResult<OrderEntity, Error, OrderEntity> {
    const queryClient = useQueryClient();
    return useMutation<OrderEntity, Error, OrderEntity>({
        mutationFn: (data) => apiClient.updateOrder(data),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['updateOrder'] });
        },
    });
}

export function useaddLineItem(): UseMutationResult<OrderLineEntity, Error, OrderLineEntity> {
    const queryClient = useQueryClient();
    return useMutation<OrderLineEntity, Error, OrderLineEntity>({
        mutationFn: (data) => apiClient.addLineItem(data),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['addLineItem'] });
        },
    });
}

export function usedeleteLineItem(): UseMutationResult<void, Error, { orderId: number; itemId: number }> {
    const queryClient = useQueryClient();
    return useMutation<void, Error, { orderId: number; itemId: number }>({
        mutationFn: (data) => apiClient.deleteLineItem(data),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['deleteLineItem'] });
        },
    });
}
