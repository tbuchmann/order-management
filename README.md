# Order Management

This repository contains a full-stack **Order Management** application with:

- a **Spring Boot** backend (`/backend`)
- a **React + TypeScript + Vite** frontend (`/frontend`)

Most parts of the application were generated with **MoProCo** from the DSL files in `/dsl`.  
After generation, missing parts were completed using **OpenCode + MoProCo-Skill** with the **GLM-5.2** LLM.

## Repository Structure

- `/backend` – Spring Boot REST API, persistence layer, security config, Flyway migrations, and tests
- `/frontend` – React UI for listing and managing orders
- `/dsl` – source DSLs used for generation:
  - `orders_entities.ment`
  - `orders-API.mapi`
  - `ordersUI.mfe`
  - `orders-requirements.mrc`

## Backend (Spring Boot)

### Tech Stack

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Spring Security (JWT filter-based)
- Flyway
- H2 (default local runtime DB), PostgreSQL driver included
- Maven Wrapper (`./mvnw`)

### Run Backend

```bash
cd /home/runner/work/order-management/order-management/backend
./mvnw spring-boot:run
```

Default local URL: `http://localhost:8080`

### Backend Tests

```bash
cd /home/runner/work/order-management/order-management/backend
./mvnw test
```

### Core API Area

The main implemented API domain is under:

- `GET /api/v1/orders`
- `GET /api/v1/orders/{id}`
- `POST /api/v1/orders`
- `PUT /api/v1/orders/{id}`
- `GET /api/v1/orders/{orderId}/items`
- `POST /api/v1/orders/{orderId}/items`
- `DELETE /api/v1/orders/{orderId}/items/{itemId}`

## Frontend (React)

### Tech Stack

- React 19
- TypeScript
- Vite
- Native fetch-based API client

### Run Frontend

```bash
cd /home/runner/work/order-management/order-management/frontend
npm install
npm run dev
```

Default local URL: `http://localhost:5173`

### Frontend Scripts

```bash
npm run dev
npm run build
npm run lint
npm run preview
```

### Backend URL Configuration

The frontend uses:

- `VITE_API_URL` environment variable, or
- defaults to `http://localhost:8080`

## DSL-Driven Development

The DSLs in `/dsl` define:

- domain entities and relationships
- roles and business rules
- API contracts and security roles
- UI pages and actions

MoProCo uses these artifacts to generate substantial parts of backend and frontend code, helping keep implementation aligned with requirements.

## Notes

- The default backend configuration is set up for local development with in-memory H2.
- JWT settings exist in backend properties for local use; production hardening is required before deployment.
