CREATE TABLE IF NOT EXISTS categories(
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT pk_categories_id PRIMARY KEY(id),
    CONSTRAINT uk_categories_name UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS products(
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    sku VARCHAR(100) NOT NULL,
    created_at DATETIME NOT NULL,
    category_id BIGINT NOT NULL,
    CONSTRAINT pk_products_id PRIMARY KEY(id),
    CONSTRAINT fk_products_categories_category_id FOREIGN KEY(category_id) REFERENCES categories(id),
    CONSTRAINT uk_products_sku UNIQUE(sku)
);

CREATE TABLE IF NOT EXISTS users(
    id BIGINT AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users_id PRIMARY KEY(id),
    CONSTRAINT uk_users_username UNIQUE(username),
    CONSTRAINT uk_users_email UNIQUE(email)
);
