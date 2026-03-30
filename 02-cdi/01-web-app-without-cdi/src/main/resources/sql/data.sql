-- Limpieza segura respetando foreign keys
SET
FOREIGN_KEY_CHECKS = 0;     -- Desactiva temporalmente las restricciones
TRUNCATE TABLE categories;
TRUNCATE TABLE products;
TRUNCATE TABLE users;
SET
FOREIGN_KEY_CHECKS = 1;     -- Reactiva las restricciones

INSERT INTO categories(name)
VALUES ('Laptops'),
       ('Computadoras de Escritorio'),
       ('Periféricos'),
       ('Componentes'),
       ('Accesorios');

INSERT INTO products (name, price, sku, created_at, category_id)
VALUES ('Laptop Lenovo IdeaPad 3', 2499.90, 'SKU-LAP-001', NOW(), 1),
       ('Laptop HP Pavilion 15', 3299.90, 'SKU-LAP-002', NOW(), 1),
       ('Laptop ASUS VivoBook 14', 2799.90, 'SKU-LAP-003', NOW(), 1),

       ('PC Gamer Ryzen 5 5600G', 3599.90, 'SKU-PC-001', NOW(), 2),
       ('PC Gamer Intel i5 RTX 4060', 5899.90, 'SKU-PC-002', NOW(), 2),
       ('PC Oficina Intel i3 12th Gen', 2199.90, 'SKU-PC-003', NOW(), 2),

       ('Mouse Logitech G203', 129.90, 'SKU-PER-001', NOW(), 3),
       ('Teclado Mecánico Redragon Kumara', 199.90, 'SKU-PER-002', NOW(), 3),
       ('Monitor LG 24" Full HD', 649.90, 'SKU-PER-003', NOW(), 3),

       ('Memoria RAM Kingston 16GB DDR4', 289.90, 'SKU-COM-001', NOW(), 4),
       ('SSD Kingston NV2 1TB', 349.90, 'SKU-COM-002', NOW(), 4),
       ('Tarjeta de Video RTX 4060 8GB', 1899.90, 'SKU-COM-003', NOW(), 4),

       ('Mouse Pad Gamer XL', 59.90, 'SKU-ACC-001', NOW(), 5),
       ('Soporte para Laptop Aluminio', 79.90, 'SKU-ACC-002', NOW(), 5),
       ('Hub USB-C 6 en 1', 149.90, 'SKU-ACC-003', NOW(), 5);

INSERT INTO users (username, password, email)
VALUES ('martin', '123456', 'martin@example.com'),
       ('carlos', '123456', 'carlos@example.com'),
       ('ana', '123456', 'ana@example.com'),
       ('luisa', '123456', 'luisa@example.com'),
       ('diego', '123456', 'diego@example.com');
