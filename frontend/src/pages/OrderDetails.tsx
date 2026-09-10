import { useState, useEffect } from 'react';
import { getOrderDetails, listLineItems, updateOrder, createOrder, addLineItem, deleteLineItem, listProducts } from '../frontend/hooks/orders-api';
import { navigate } from '../frontend/navigate';
import type { OrderEntity } from '../types/OrderEntity';
import type { OrderLineEntity } from '../types/OrderLineEntity';
import type { ProductEntity } from '../types/ProductEntity';

const ORDER_STATUSES = ['open', 'waitingForStock', 'packaged', 'shipped', 'canceled', 'returned'];

interface DraftLineItem {
    productId: number;
    productName: string;
    quantity: number;
    unit: string;
    unitPrice: number;
    currency: string;
}

export default function OrderDetails() {
    const pathParts = window.location.pathname.split('/');
    const lastPart = pathParts[pathParts.length - 1];
    const isNew = lastPart === 'new';
    const orderId = isNew ? null : Number(lastPart);

    const [order, setOrder] = useState<OrderEntity | null>(null);
    const [orderLoading, setOrderLoading] = useState(!isNew);
    const [lineItems, setLineItems] = useState<OrderLineEntity[]>([]);
    const [lineItemsLoading, setLineItemsLoading] = useState(!isNew);
    const [selectedItemId, setSelectedItemId] = useState<number | null>(null);
    const [products, setProducts] = useState<ProductEntity[]>([]);
    const [draftItems, setDraftItems] = useState<DraftLineItem[]>([]);
    const [saving, setSaving] = useState(false);

    const [newItem, setNewItem] = useState<Omit<OrderLineEntity, 'id'>>({
        quantity: 1,
        unit: 'pcs',
        unitPrice: 0,
        currency: 'EUR',
    });
    const [selectedProductId, setSelectedProductId] = useState<number | ''>('');

    useEffect(() => {
        listProducts()
            .then((data) => setProducts(Array.isArray(data) ? data : []))
            .catch(() => setProducts([]));
    }, []);

    useEffect(() => {
        if (orderId === null || Number.isNaN(orderId)) return;
        getOrderDetails(orderId)
            .then((data) => { setOrder(data); setOrderLoading(false); })
            .catch(() => setOrderLoading(false));
    }, [orderId]);

    useEffect(() => {
        if (orderId === null || Number.isNaN(orderId)) return;
        listLineItems(orderId)
            .then((data) => { setLineItems(Array.isArray(data) ? data : []); setLineItemsLoading(false); })
            .catch(() => setLineItemsLoading(false));
    }, [orderId]);

    if (orderLoading) return <div>Loading...</div>;

    const updateOrderField = (field: keyof OrderEntity, value: string | number) => {
        setOrder((prev) => ({
            ...prev,
            id: prev?.id ?? 0,
            orderDate: prev?.orderDate ?? '',
            desiredShippingDate: prev?.desiredShippingDate ?? '',
            orderStatus: prev?.orderStatus ?? 'open',
            orderValue: prev?.orderValue ?? 0,
            [field]: value,
        }));
    };

    const handleAddDraftItem = () => {
        if (selectedProductId === '') return;
        const product = products.find((p) => p.id === Number(selectedProductId));
        if (!product) return;
        setDraftItems([...draftItems, {
            productId: product.id,
            productName: product.productName,
            quantity: newItem.quantity,
            unit: newItem.unit,
            unitPrice: newItem.unitPrice,
            currency: newItem.currency,
        }]);
        setNewItem({ quantity: 1, unit: 'pcs', unitPrice: 0, currency: 'EUR' });
        setSelectedProductId('');
    };

    const handleRemoveDraftItem = (index: number) => {
        setDraftItems(draftItems.filter((_, i) => i !== index));
    };

    const handleSaveOrder = async () => {
        setSaving(true);
        try {
            if (isNew) {
                const calculatedValue = draftItems.reduce((sum, item) => sum + item.quantity * item.unitPrice, 0);
                const newOrder: Omit<OrderEntity, 'id'> = {
                    orderDate: order?.orderDate ?? new Date().toISOString().split('T')[0],
                    desiredShippingDate: order?.desiredShippingDate ?? new Date().toISOString().split('T')[0],
                    orderStatus: order?.orderStatus ?? 'open',
                    orderValue: calculatedValue,
                };
                const created = await createOrder(newOrder);
                for (const draft of draftItems) {
                    await addLineItem(created.id, {
                        quantity: draft.quantity,
                        unit: draft.unit,
                        unitPrice: draft.unitPrice,
                        currency: draft.currency,
                        productId: draft.productId,
                    });
                }
                navigate(`/orders/${created.id}`);
            } else if (order && orderId !== null) {
                const updated = await updateOrder(orderId, order);
                setOrder(updated);
            }
        } finally {
            setSaving(false);
        }
    };

    const handleAddLineItem = () => {
        if (orderId === null) return;
        addLineItem(orderId, newItem).then((data) => {
            setLineItems([...lineItems, data]);
            setNewItem({ quantity: 1, unit: 'pcs', unitPrice: 0, currency: 'EUR' });
            setSelectedProductId('');
        });
    };

    const handleDeleteLineItem = () => {
        if (selectedItemId !== null && orderId !== null) {
            deleteLineItem(orderId, selectedItemId)
                .then(() => setLineItems(lineItems.filter((item) => item.id !== selectedItemId)));
        }
    };

    const handleProductSelect = (productId: number) => {
        setSelectedProductId(productId);
        const product = products.find((p) => p.id === productId);
        if (product) {
            setNewItem((prev) => ({
                ...prev,
                unitPrice: product.price ?? 0,
                currency: product.currency ?? prev.currency,
            }));
        }
    };

    const renderLineItemForm = (onAdd: () => void) => (
        <table className="form-table">
            <tbody>
                <tr>
                    <td><label htmlFor="productSelect">Product</label></td>
                    <td>
                        <select
                            id="productSelect"
                            value={selectedProductId}
                            onChange={(e) => handleProductSelect(Number(e.target.value))}
                        >
                            <option value="">-- Select product --</option>
                            {products.map((p) => (
                                <option key={p.id} value={p.id}>
                                    {p.productName} ({p.productCategory})
                                </option>
                            ))}
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label htmlFor="itemQuantity">Quantity</label></td>
                    <td>
                        <input
                            id="itemQuantity"
                            type="number"
                            value={newItem.quantity}
                            onChange={(e) => setNewItem({ ...newItem, quantity: Number(e.target.value) })}
                        />
                    </td>
                </tr>
                <tr>
                    <td><label htmlFor="itemUnit">Unit</label></td>
                    <td>
                        <input
                            id="itemUnit"
                            type="text"
                            value={newItem.unit}
                            onChange={(e) => setNewItem({ ...newItem, unit: e.target.value })}
                        />
                    </td>
                </tr>
                <tr>
                    <td><label htmlFor="itemUnitPrice">Unit Price</label></td>
                    <td>
                        <input
                            id="itemUnitPrice"
                            type="number"
                            step="0.01"
                            value={newItem.unitPrice}
                            onChange={(e) => setNewItem({ ...newItem, unitPrice: Number(e.target.value) })}
                        />
                    </td>
                </tr>
                <tr>
                    <td><label htmlFor="itemCurrency">Currency</label></td>
                    <td>
                        <select
                            id="itemCurrency"
                            value={newItem.currency}
                            onChange={(e) => setNewItem({ ...newItem, currency: e.target.value })}
                        >
                            <option value="EUR">EUR</option>
                            <option value="USD">USD</option>
                            <option value="GBP">GBP</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colSpan={2}>
                        <button onClick={onAdd} disabled={selectedProductId === ''}>Add to list</button>
                    </td>
                </tr>
            </tbody>
        </table>
    );

    return (
        <div className="page-orderdetails">
            <h1>{isNew ? 'Create Order' : 'Order Details'}</h1>

            <div className="form-section">
                <h2>Order</h2>
                <table className="form-table">
                    <tbody>
                        <tr>
                            <td><label htmlFor="orderDate">Order Date</label></td>
                            <td>
                                <input
                                    id="orderDate"
                                    type="date"
                                    value={order?.orderDate ?? ''}
                                    onChange={(e) => updateOrderField('orderDate', e.target.value)}
                                />
                            </td>
                        </tr>
                        <tr>
                            <td><label htmlFor="desiredShippingDate">Desired Shipping Date</label></td>
                            <td>
                                <input
                                    id="desiredShippingDate"
                                    type="date"
                                    value={order?.desiredShippingDate ?? ''}
                                    onChange={(e) => updateOrderField('desiredShippingDate', e.target.value)}
                                />
                            </td>
                        </tr>
                        <tr>
                            <td><label htmlFor="orderStatus">Order Status</label></td>
                            <td>
                                <select
                                    id="orderStatus"
                                    value={order?.orderStatus ?? 'open'}
                                    onChange={(e) => updateOrderField('orderStatus', e.target.value)}
                                >
                                    {ORDER_STATUSES.map((s) => <option key={s} value={s}>{s}</option>)}
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td><label htmlFor="orderValue">Order Value</label></td>
                            <td>
                                <input
                                    id="orderValue"
                                    type="number"
                                    step="0.01"
                                    readOnly
                                    value={isNew
                                        ? draftItems.reduce((sum, item) => sum + item.quantity * item.unitPrice, 0)
                                        : (order?.orderValue ?? 0)}
                                />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            {isNew && (
                <div className="form-section">
                    <h2>Line Items</h2>
                    {draftItems.length > 0 && (
                        <table>
                            <thead>
                                <tr>
                                    <th>Product</th>
                                    <th>Quantity</th>
                                    <th>Unit</th>
                                    <th>Unit Price</th>
                                    <th>Currency</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {draftItems.map((item, i) => (
                                    <tr key={i}>
                                        <td>{item.productName}</td>
                                        <td>{item.quantity}</td>
                                        <td>{item.unit}</td>
                                        <td>{item.unitPrice}</td>
                                        <td>{item.currency}</td>
                                        <td><button onClick={() => handleRemoveDraftItem(i)}>Remove</button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    )}
                    <h3>Add Line Item</h3>
                    {renderLineItemForm(handleAddDraftItem)}
                </div>
            )}

            {!isNew && !lineItemsLoading && (
                <div className="form-section">
                    <h2>Line Items</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Product</th>
                                <th>Quantity</th>
                                <th>Unit</th>
                                <th>Unit Price</th>
                                <th>Currency</th>
                            </tr>
                        </thead>
                        <tbody>
                            {lineItems.map((item) => (
                                <tr
                                    key={item.id}
                                    onClick={() => setSelectedItemId(item.id)}
                                    style={{
                                        cursor: 'pointer',
                                        backgroundColor: selectedItemId === item.id ? '#e0e0e0' : undefined,
                                    }}
                                >
                                    <td>{item.id}</td>
                                    <td>{item.productName ?? item.productEntity?.productName ?? '-'}</td>
                                    <td>{item.quantity}</td>
                                    <td>{item.unit}</td>
                                    <td>{item.unitPrice}</td>
                                    <td>{item.currency}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>

                    <h3>Add Line Item</h3>
                    {renderLineItemForm(handleAddLineItem)}
                </div>
            )}

            <div className="actions">
                <button onClick={handleSaveOrder} disabled={saving}>
                    {saving ? 'Saving...' : isNew ? 'Create Order' : 'Update Order'}
                </button>
                {!isNew && <button onClick={handleDeleteLineItem} disabled={selectedItemId === null}>Delete Line Item</button>}
                <button onClick={() => navigate('/orders')}>Back to List</button>
            </div>
        </div>
    );
}
