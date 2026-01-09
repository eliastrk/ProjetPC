CREATE TABLE IF NOT EXISTS orders (
    delivery_id   VARCHAR(64) PRIMARY KEY,
    order_id      VARCHAR(64) NOT NULL,
    customer_id   VARCHAR(64) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    customer_addr TEXT NOT NULL,
    status        VARCHAR(32) NOT NULL,
    order_date    TIMESTAMP NOT NULL,
    total_price   NUMERIC(10,2) NOT NULL
);
