CREATE TABLE app_user (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    job_title VARCHAR(120) NOT NULL,
    role VARCHAR(40) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE product (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    sku VARCHAR(60) NOT NULL UNIQUE,
    name VARCHAR(160) NOT NULL,
    description VARCHAR(500),
    price DECIMAL(19,2) NOT NULL,
    stock INT NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT chk_product_price CHECK (price >= 0),
    CONSTRAINT chk_product_stock CHECK (stock >= 0),
    INDEX idx_product_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE customer (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(40) NOT NULL UNIQUE,
    name VARCHAR(160) NOT NULL,
    phone VARCHAR(50),
    email VARCHAR(180),
    city VARCHAR(120),
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    INDEX idx_customer_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE supplier (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(40) NOT NULL UNIQUE,
    name VARCHAR(160) NOT NULL,
    contact VARCHAR(160),
    phone VARCHAR(50),
    email VARCHAR(180),
    city VARCHAR(120),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    INDEX idx_supplier_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sale (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    invoice_no VARCHAR(70) NOT NULL UNIQUE,
    product_id BIGINT NOT NULL,
    product_name VARCHAR(160) NOT NULL,
    customer_name VARCHAR(160) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(19,2) NOT NULL,
    total DECIMAL(19,2) NOT NULL,
    payment_method VARCHAR(40) NOT NULL,
    cashier VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PAID',
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_sale_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT chk_sale_quantity CHECK (quantity > 0),
    CONSTRAINT chk_sale_unit_price CHECK (unit_price >= 0),
    CONSTRAINT chk_sale_total CHECK (total >= 0),
    INDEX idx_sale_created_at (created_at),
    INDEX idx_sale_cashier (cashier)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE activity (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    actor VARCHAR(50) NOT NULL,
    action VARCHAR(50) NOT NULL,
    entity VARCHAR(50) NOT NULL,
    detail VARCHAR(700) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    INDEX idx_activity_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
