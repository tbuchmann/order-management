# Order Management System — LLM-Only Implementation Spec

## Purpose

This document is a plain-English specification for an order management system. It describes **what** to build, not **how** to build it. An LLM receiving this spec must design and implement the full stack from scratch — no code generator, no DSL, no scaffolding tool. The goal is to produce a functionally equivalent system to the moproco-generated order management example for comparison purposes.

## Tech Stack Constraints

The implementation must use:

- **Backend:** Spring Boot 4.x, Java 21, JPA/Hibernate, Flyway for database migrations, H2 in-memory database (dev profile)
- **Frontend:** React with TypeScript, Vite, React Query (@tanstack/react-query) for data fetching, React Router for navigation, Zod for validation
- **Build:** Maven for backend, npm for frontend
- **Database:** H2 in-memory (dev), schema managed by Flyway

## Domain Overview

The system manages customer orders for a product distribution company. Sales clerks create and manage orders containing multiple line items (products with quantities and prices). Warehouse clerks prepare shipments based on order details. Orders track their status through a lifecycle (open → waiting for stock → packaged → shipped, or canceled/returned). The system maintains product catalog, stock levels, and price lists.

## Entities & Attributes

### Order
An order represents a customer's request for products. It has:
- An order date (when the order was placed)
- A desired shipping date (when the customer wants delivery)
- A status (see Order Status enum below)
- An order value (the total monetary value of the order — this is a derived/calculated field, not directly settable)
- A reference to the customer who placed the order
- A reference to a shipping address
- A reference to a billing address
- A list of line items (the individual products and quantities ordered)

### Customer
A customer is a business entity that places orders. It has:
- A company name
- A phone number
- An email address
- A DUNS number (a unique business identifier)
- A reference to a company address

### Delivery Address
A postal address used for shipping or billing. It has:
- Street (street name and number)
- Postcode
- City
- Country

### Order Line
A line item within an order, representing a product and quantity. It has:
- A reference to the order it belongs to
- A reference to the product being ordered
- A quantity (how many units)
- A unit (e.g., "pieces", "boxes", "kg")
- A unit price (the price per unit at time of ordering)

### Product
A product in the catalog. It has:
- A product name
- A product category

### Price List Entry
A pricing record that links a product to a current price. It has:
- A reference to the product
- A price (the monetary amount)

### Warehouse
A warehouse that holds stock. It has:
- A list of items in stock

### Warehouse Item
A stock record for a specific product in a warehouse. It has:
- A reference to the product
- A current stock level (how many units are available)
- A unit of measurement
- A minimum stock level (threshold for reordering)

### Order Status (Enum)
An order can be in one of these states:
- open
- waitingForStock
- packaged
- shipped
- canceled
- returned

## Relationships

- An Order belongs to one Customer (many-to-one)
- An Order has one shipping address and one billing address (many-to-one to Delivery Address for each)
- An Order has many Order Lines (one-to-many)
- An Order Line belongs to one Order (many-to-one)
- An Order Line references one Product (many-to-one)
- A Customer has one company address (many-to-one to Delivery Address)
- A Price List Entry references one Product (many-to-one)
- A Warehouse has many Warehouse Items (one-to-many)
- A Warehouse Item references one Product (many-to-one)

## Business Rules

1. **Quantity must be greater than zero** — every order line item must have a quantity of at least 1.
2. **Stock must not be negative** — warehouse stock levels can never go below zero.
3. **Desired shipping date must be in the future** — when creating an order, the desired shipping date must be at least one day after the current date.
4. **Order value is the sum of line items** — the order value is automatically calculated as the sum of (quantity × unit price) across all line items. It is not set manually.
5. **Unit price must match list price** — the unit price on an order line must equal the price listed in the Price List Entry for that product at the order date.
6. **Orders must not be deleted** — orders may be canceled but must never be deleted, due to regulatory compliance.
7. **Orders can only be canceled before shipping** — once an order has been shipped, it cannot be canceled.

## User Roles

### Sales Clerk
A sales clerk creates and manages orders. They can:
- Create orders
- View orders
- Update orders
- Add and update order line items
- Delete order line items
- View products and price list entries

### User (General)
A general user can view orders but not modify them. They can:
- View orders

### Warehouse Clerk
A warehouse clerk prepares shipments and manages stock. They can:
- View orders and order line items
- View and update warehouse stock items
- View products

## User Stories

### Story 1: Create Order
**As a** Sales Clerk,
**I want to** create an order with all the required attributes,
**So that** they are documented in the system and are subtracted from stock.

**Acceptance criteria:**
- Given I am logged in and authorized as a sales clerk
- When I submit an order
- Then an order is created in the system and the stock is reduced accordingly

### Story 2: List Open Orders
**As a** User,
**I want to** see all orders that are not yet delivered,
**So that** I can pick one to process.

### Story 3: Get Order Details
**As a** Warehouse Clerk,
**I want to** see the order details of an order with a specified ID,
**So that** I can prepare the ordered products.

### Story 4: Update Order Details
**As a** Sales Clerk,
**I want to** change the ordered quantities and line items when the customer demands it,
**So that** we don't ship goods that are not wanted anymore.

### Story 5: Add Line Item
**As a** Sales Clerk,
**I want to** add additional line items to an existing order,
**So that** the system can store the references to the products.

### Story 6: Delete Line Item
**As a** Sales Clerk,
**I want to** delete an existing line item,
**So that** it is removed from the order.

**Acceptance criteria:**
- Given the line item exists
- When it was already subtracted from stock
- Then the stock should be added again to keep the balance correct

## API Requirements

The backend must expose a REST API with the following endpoints. All endpoints are secured (require authentication). Base paths are shown for reference.

### Orders API (base path: `/api/v1/orders`)

1. **List Orders** — `GET /api/v1/orders`
   - Optional query parameter: `orderStatus` (string) to filter orders by status
   - Returns a paginated response (page size 20, sorted by order date descending)
   - Each order in the response includes: order ID, order date, customer's company name, order value, and order status
   - Accessible by: User, Sales Clerk, Warehouse Clerk

2. **Get Order Details** — `GET /api/v1/orders/{id}`
   - Path parameter: `id` (the order's ID)
   - Returns the full order details including all fields and the customer's company name
   - Accessible by: Sales Clerk, Warehouse Clerk

3. **Create Order** — `POST /api/v1/orders`
   - Request body: the order data (date, desired shipping date, status, customer reference, line items)
   - Returns the created order with its generated ID
   - HTTP status: 201
   - Accessible by: Sales Clerk

4. **Update Order** — `PUT /api/v1/orders/{id}`
   - Path parameter: `id` (the order's ID)
   - Request body: the updated order data
   - Returns the updated order
   - Accessible by: Sales Clerk

5. **List Line Items** — `GET /api/v1/orders/{orderId}/items`
   - Path parameter: `orderId`
   - Returns all line items for the specified order
   - Accessible by: Sales Clerk, Warehouse Clerk

6. **Add Line Item** — `POST /api/v1/orders/{orderId}/items`
   - Path parameter: `orderId`
   - Request body: the line item data (product, quantity, unit, unit price)
   - The order relationship is automatically set from the path parameter (the line item belongs to the order in the URL)
   - Returns the created line item
   - HTTP status: 201
   - Accessible by: Sales Clerk

7. **Delete Line Item** — `DELETE /api/v1/orders/{orderId}/items/{itemId}`
   - Path parameters: `orderId`, `itemId`
   - Returns nothing (void)
   - Accessible by: Sales Clerk

### Products API (base path: `/api/v1/products`)

8. **List Products** — `GET /api/v1/products`
   - No parameters
   - Returns all products
   - Accessible by: Sales Clerk, Warehouse Clerk, User

## UI Requirements

The frontend must provide the following pages with navigation between them:

### Home Page
- Route: `/`
- A landing page with a button/link to view the orders list

### Orders List Page
- Route: `/orders`
- Displays a table of orders with columns: Order ID, Order Date, Customer name, Order Total, Status
- Each row has a button to view the order's details
- A button to create a new order
- Data is fetched from the list orders API endpoint

### Order Details Page
- Route: `/orders/{id}`
- Displays the order's fields: Order ID (read-only), Order Date (date picker), Desired Shipping Date (date picker), Customer name (read-only text), Order Total (read-only), Status (dropdown)
- Displays a table of line items for this order with columns: Product, Quantity, Unit, Unit Price
- A sub-form to add a new line item with: Product (dropdown populated from products list), Quantity (number input), Unit (text input), Unit Price (number input)
- An "Add" button to add the line item to the order
- A "Remove" button on each line item row
- Action buttons: Update Order, Add Line Item, Delete Line Item
- Data is fetched from: get order details, list line items, and list products endpoints

### Create Order Page
- Route: `/orders/new`
- A form with fields: Order Date (date picker), Desired Shipping Date (date picker), Status (dropdown), Order Total (read-only)
- A sub-form to add line items with: Product (dropdown from products list), Quantity (number input), Unit (text input), Unit Price (read-only, auto-filled from price list)
- An "Add" button to add line items to the form
- A "Save" button to submit the order
- Data is fetched from: list products endpoint

## Deliverables

The LLM must produce:

### Backend
- JPA entity classes for all 8 entities (including the Order Status enum)
- Spring Data JPA repository interfaces for each entity
- Data Transfer Objects (DTOs) for API responses with appropriate field mapping (e.g., flattening relations to IDs and display names where appropriate)
- REST controller classes for the Orders API and Products API
- Service layer with business logic for all operations
- Flyway SQL migration script(s) to create the database schema
- JUnit tests for repository and validation layers
- Spring Boot application configuration (application.properties)
- Security configuration (JWT-based, with role-based access control)
- CORS configuration for frontend access
- Maven `pom.xml` with all dependencies

### Frontend
- TypeScript type definitions for all entities
- Zod validation schemas for form data
- React Query hooks for all API endpoints (with proper query keys, path parameter handling, and query string construction)
- React page components for all 4 pages (Home, Orders List, Order Details, Create Order)
- An API client utility for making HTTP requests
- React Router setup with routes for all pages
- Vite configuration
- `package.json` with all dependencies
- TypeScript configuration

### Quality Requirements
- The backend must start without errors on the H2 dev profile
- The frontend must compile without TypeScript errors
- All API endpoints must be reachable (no 403/500 errors on basic operations)
- The order value must be automatically calculated from line items
- Filtering orders by status must work correctly (empty filter returns all orders)
- Pagination must work for the list orders endpoint
- Form inputs must use correct types (numbers for numeric fields, not strings)
- Path parameters must be correctly interpolated in API URLs (including base paths)
