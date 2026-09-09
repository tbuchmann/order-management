import { useSyncExternalStore } from 'react';
import OrdersList from './pages/OrdersList';
import OrderDetails from './pages/OrderDetails';

function subscribe(callback: () => void) {
    window.addEventListener('popstate', callback);
    return () => window.removeEventListener('popstate', callback);
}

function getSnapshot() {
    return window.location.pathname;
}

function App() {
    const pathname = useSyncExternalStore(subscribe, getSnapshot);

    if (pathname === '/orders' || pathname === '/orders/') {
        return <OrdersList />;
    }

    const orderMatch = pathname.match(/^\/orders\/(\d+)$/);
    if (orderMatch) {
        return <OrderDetails />;
    }

    if (pathname === '/orders/new') {
        return <OrderDetails />;
    }

    return (
        <div>
            <h1>Order Management</h1>
            <a href="/orders">Go to Orders</a>
        </div>
    );
}

export default App;
