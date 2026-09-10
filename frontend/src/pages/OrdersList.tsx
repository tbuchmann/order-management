import { useState, useEffect } from 'react';
import { listOrders } from '../frontend/hooks/orders-api';
import { navigate } from '../frontend/navigate';
import type { OrderEntity } from '../types/OrderEntity';

export default function OrdersList() {
    const [orders, setOrders] = useState<OrderEntity[]>([]);
    const [ordersLoading, setOrdersLoading] = useState(true);

    useEffect(() => {
        listOrders()
            .then((page) => {
                setOrders(page.content ?? []);
                setOrdersLoading(false);
            })
            .catch(() => setOrdersLoading(false));
    }, []);

    if (ordersLoading) return <div>Loading...</div>;

    const createOrder = () => {
        navigate('/orders/new');
    };

    return (
        <div className="page-orderslist">
            <h1>OrdersList</h1>
            <table>
                <thead><tr><th>Order ID</th><th>Order Date</th><th>Customer</th><th>Order Total</th><th>Status</th><th></th></tr></thead>
                <tbody>
                    {orders?.map((item, i) => (
                        <tr key={i}>
                            <td>{item.id}</td>
                            <td>{item.orderDate}</td>
                            <td>{item.customerEntity?.companyName}</td>
                            <td>{item.orderValue.toFixed(2)}</td>
                            <td>{item.orderStatus}</td>
                            <td><button onClick={() => navigate(`/orders/${item.id}`)}>View</button></td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div className="actions">
            <button onClick={createOrder}>CreateOrder</button>
            </div>
        </div>
    );
}
