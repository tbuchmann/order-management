import { useState, useEffect } from 'react';
import { apiClient } from '../frontend/hooks/api-client';
import type { OrderEntity } from '../types/OrderEntity';
import type { OrderLineEntity } from '../types/OrderLineEntity';

export default function OrderDetails() {
    const pathParts = window.location.pathname.split('/');
    const orderId = Number(pathParts[pathParts.length - 1]);

    const [order, setOrder] = useState<OrderEntity | null>(null);
    const [orderLoading, setOrderLoading] = useState(true);
    const [lineItems, setLineItems] = useState<OrderLineEntity[]>([]);
    const [lineItemsLoading, setLineItemsLoading] = useState(true);
    const [selectedItemId, setSelectedItemId] = useState<number | null>(null);

    useEffect(() => {
        apiClient.get<OrderEntity>(`/api/v1/orders/${orderId}`)
            .then((data) => { setOrder(data); setOrderLoading(false); })
            .catch(() => setOrderLoading(false));
    }, [orderId]);

    useEffect(() => {
        apiClient.get<OrderLineEntity[]>(`/api/v1/orders/${orderId}/items`)
            .then((data) => { setLineItems(Array.isArray(data) ? data : []); setLineItemsLoading(false); })
            .catch(() => setLineItemsLoading(false));
    }, [orderId]);

    if (orderLoading) return <div>Loading...</div>;

    const updateOrder = () => {
        if (order) {
            apiClient.put<OrderEntity>(`/api/v1/orders/${orderId}`, order)
                .then((data) => setOrder(data));
        }
    };

    const addLineItem = () => {
        const newItem: Partial<OrderLineEntity> = {
            quantity: 1,
            unit: 'pcs',
            unitPrice: 0,
            currency: 'EUR',
        };
        apiClient.post<OrderLineEntity>(`/api/v1/orders/${orderId}/items`, newItem)
            .then((data) => setLineItems([...lineItems, data]));
    };

    const deleteLineItem = () => {
        if (selectedItemId !== null) {
            apiClient.delete<void>(`/api/v1/orders/${orderId}/items/${selectedItemId}`)
                .then(() => setLineItems(lineItems.filter((item) => item.lineNumber !== selectedItemId)));
        }
    };

    return (
        <div className="page-orderdetails">
            <h1>OrderDetails</h1>
            <table>
                <thead><tr><th>Order ID</th><th>Order Date</th><th>Customer</th><th>Order Total</th><th>Status</th></tr></thead>
                <tbody>
                    {order && (
                        <tr>
                            <td>{order.orderId}</td>
                            <td>{order.orderDate}</td>
                            <td>{order.customerEntity?.companyName}</td>
                            <td>{order.orderValue}</td>
                            <td>{order.orderStatus}</td>
                        </tr>
                    )}
                </tbody>
            </table>
            {!lineItemsLoading && (
                <table>
                    <thead><tr><th>Line Number</th><th>Quantity</th><th>Unit</th><th>Unit Price</th><th>Currency</th></tr></thead>
                    <tbody>
                        {lineItems.map((item, i) => (
                            <tr key={i} onClick={() => setSelectedItemId(item.lineNumber)} style={{ cursor: 'pointer' }}>
                                <td>{item.lineNumber}</td>
                                <td>{item.quantity}</td>
                                <td>{item.unit}</td>
                                <td>{item.unitPrice}</td>
                                <td>{item.currency}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
            <div className="actions">
            <button onClick={updateOrder}>UpdateOrder</button>
            <button onClick={addLineItem}>AddLineItem</button>
            <button onClick={deleteLineItem}>DeleteLineItem</button>
            </div>
        </div>
    );
}
