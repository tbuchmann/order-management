import { useState, useEffect } from 'react';
import { apiClient } from '../frontend/hooks/api-client';
import type { OrderEntity } from '../types/OrderEntity';

export default function OrdersList() {
    const [orders, setOrders] = useState<OrderEntity[]>([]);
    const [ordersLoading, setOrdersLoading] = useState(true);
    const [selectedId, setSelectedId] = useState<number | null>(null);

    useEffect(() => {
        apiClient.get<OrderEntity[]>('/api/v1/orders')
            .then((data) => {
                setOrders(Array.isArray(data) ? data : []);
                setOrdersLoading(false);
            })
            .catch(() => setOrdersLoading(false));
    }, []);

    if (ordersLoading) return <div>Loading...</div>;

    const viewOrderDetails = () => {
        if (selectedId !== null) {
            window.location.href = `/orders/${selectedId}`;
        }
    };

    const createOrder = () => {
        window.location.href = '/orders/new';
    };

    return (
        <div className="page-orderslist">
            <h1>OrdersList</h1>
            <table>
                <thead><tr><th>Order ID</th><th>Order Date</th><th>Customer</th><th>Order Total</th><th>Status</th></tr></thead>
                <tbody>
                    {orders?.map((item, i) => (
                        <tr key={i} onClick={() => setSelectedId(item.orderId)} style={{ cursor: 'pointer' }}>
                            <td>{item.orderId}</td>
                            <td>{item.orderDate}</td>
                            <td>{item.customerEntity?.companyName}</td>
                            <td>{item.orderValue}</td>
                            <td>{item.orderStatus}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div className="actions">
            <button onClick={viewOrderDetails}>ViewOrderDetails</button>
            <button onClick={createOrder}>CreateOrder</button>
            </div>
        </div>
    );
}
