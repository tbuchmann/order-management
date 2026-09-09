entity Order {
    orderId: Number
    orderDate: Date
    desiredShippingDate: Date
    customer: Customer
    shippingAddress: DeliveryAddress
    billingAddress: DeliveryAddress
    lineItems: OrderLine
    orderValue: Currency derived
    orderStatus: enum OrderStatus
}

entity Customer {
    customerId: Number
    companyName: String
    phoneNumber: String
    companyEmail: Email
    dunsNumber: Number
    companyAddress: DeliveryAddress
}

entity DeliveryAddress {
    street: String
    postcode: String
    city: String
    country: String
}

entity OrderLine {
    lineNumber: Number
    order: Order
    product: Product
    quantity: Number
    unit: String
    unitPrice: Currency
}

entity Product {
    productNumber: Number
    productName: String
    productCategory: String
}

entity PriceListEntry {
    pleId: Number
    product: Product
    price: Currency
}

entity Warehouse {
    productsInStock: WarehouseItem
}

entity WarehouseItem {
    product: Product
    currentStock: Number
    unit: String
    minimumStockLevel: Number
}

enum OrderStatus {
    open,
    waitingForStock,
    packaged,
    shipped,
    canceled,
    returned
}

role SalesClerk {
    description "Sales clerk who creates and manages orders"
    create:Order
    read:Order
    update:Order
    create:OrderLine
    update:OrderLine
    delete:OrderLine
    read:Product
    read:PriceListEntry
}

role User {
    description "General user who can view orders"
    read:Order
}

role WarehouseClerk {
    description "Warehouse clerk who prepares shipments and manages stock"
    read:Order
    read:OrderLine
    read:WarehouseItem
    update:WarehouseItem
    read:Product
}

story CreateOrder {
    as [SalesClerk]
    iWant "to create an order with all the required attributes"
    soThat "they are documented in the system and are substracted from stock"
    operatesOn [Order, OrderLine, WarehouseItem]
    acceptance {
        given "I am logged in and authorized as a sales clerk"
        when "I submit an order"
        then "an order is created in the system and the stock is reduced accordingly"
    }
}

story ListOpenOrders {
    as [User]
    iWant "to see all orders that are not yet delivered"
    soThat "I can pick one to process"
    operatesOn [Order]
}

story GetOrdersDetails {
    as [WarehouseClerk]
    iWant "to see the order details of an order with a specified id"
    soThat "I can prepare the ordered products"
    operatesOn [Order, OrderLine]
}

story UpdateOrderDetails {
    as [SalesClerk]
    iWant "to change the ordered quantities and line items when the customer demands it"
    soThat "we don't ship goods that are not wanted anymore"
    operatesOn [Order, OrderLine]
}

story AddLineItem {
    as [SalesClerk]
    iWant "to add additional line items to an existing order"
    soThat "the system can store the references to the products"
    operatesOn [OrderLine, Order]
}

story DeleteLineItem {
    as [SalesClerk]
    iWant "to delete an existing line item"
    soThat "it is removed from the order"
    operatesOn [OrderLine, Order]
    acceptance {
        given "the line item exists"
        when "it was already substracted from stock"
        then "the stock should be added again to keep the balance correct"
    }
}

rule quantityMustBeGreaterZero: "Quantity must be greater than zero" appliesTo OrderLine
rule stockMustNotBeNegative: "currentStock must not be negative" appliesTo WarehouseItem
rule desiredShippingDateFuture: "desiredShippingDate must be at least one day in the future" appliesTo Order
rule orderAmountIsSum: "the orderValue in Order is the sum over all lineItems of the products of quantity times unitPrice" appliesTo Order
rule unitPriceEqualsListPrice: "the unitPrice in OrderLine must equal the price of the product in PriceListEntry at orderDate" appliesTo OrderLine
rule orderMustNotBeDeleted: "orders may be canceled but must not be deleted due to regulation conformance" appliesTo Order
rule orderCancelOnlyBeforeShipping: "orders must not be canceled after shipping" appliesTo Order
