CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    job_title VARCHAR(120) NOT NULL,
    role VARCHAR(40) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    sku VARCHAR(60) NOT NULL UNIQUE,
    name VARCHAR(160) NOT NULL,
    description VARCHAR(500),
    price NUMERIC(19,2) NOT NULL CHECK (price >= 0),
    stock INTEGER NOT NULL CHECK (stock >= 0),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(40) NOT NULL UNIQUE,
    name VARCHAR(160) NOT NULL,
    phone VARCHAR(50),
    email VARCHAR(180),
    city VARCHAR(120),
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE supplier (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(40) NOT NULL UNIQUE,
    name VARCHAR(160) NOT NULL,
    contact VARCHAR(160),
    phone VARCHAR(50),
    email VARCHAR(180),
    city VARCHAR(120),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sale (
    id BIGSERIAL PRIMARY KEY,
    invoice_no VARCHAR(70) NOT NULL UNIQUE,
    product_id BIGINT NOT NULL REFERENCES product(id),
    product_name VARCHAR(160) NOT NULL,
    customer_name VARCHAR(160) NOT NULL,
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    unit_price NUMERIC(19,2) NOT NULL CHECK (unit_price >= 0),
    total NUMERIC(19,2) NOT NULL CHECK (total >= 0),
    payment_method VARCHAR(40) NOT NULL,
    cashier VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PAID',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE activity (
    id BIGSERIAL PRIMARY KEY,
    actor VARCHAR(50) NOT NULL,
    action VARCHAR(50) NOT NULL,
    entity VARCHAR(50) NOT NULL,
    detail VARCHAR(700) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_product_name ON product(name);
CREATE INDEX idx_customer_name ON customer(name);
CREATE INDEX idx_supplier_name ON supplier(name);
CREATE INDEX idx_sale_created_at ON sale(created_at DESC);
CREATE INDEX idx_sale_cashier ON sale(cashier);
CREATE INDEX idx_activity_created_at ON activity(created_at DESC);
