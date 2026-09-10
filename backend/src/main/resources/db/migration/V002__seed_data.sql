INSERT INTO delivery_address_entity (id, street, postcode, city, country) VALUES
    (1, 'Hauptstrasse 1', '95028', 'Hof', 'Germany'),
    (2, 'Industrieweg 42', '10115', 'Berlin', 'Germany');

INSERT INTO customer_entity (customer_id, company_name, phone_number, company_email, duns_number, company_address_id) VALUES
    (1, 'TechCorp GmbH', '+49 9281 123456', 'info@techcorp.de', 123456789, 1),
    (2, 'Bauteile AG', '+49 30 9876543', 'sales@bauteile.de', 987654321, 2);

INSERT INTO product_entity (product_number, product_name, product_category) VALUES
    (1, 'Wrench Set Pro', 'Tools'),
    (2, 'Steel Beam 2m', 'Materials'),
    (3, 'Safety Helmet', 'Equipment');

INSERT INTO order_entity (order_id, order_date, desired_shipping_date, order_status, order_value, customer_id, shipping_address_id, billing_address_id) VALUES
    (1, '2026-09-01', '2026-09-15', 'open', 305.45, 1, 1, 1),
    (2, '2026-09-05', '2026-09-20', 'shipped', 980.00, 2, 2, 2);

INSERT INTO order_line_entity (line_number, quantity, unit, unit_price, currency, order_id, product_id) VALUES
    (1, 5, 'pcs', 29.99, 'EUR', 1, 1),
    (2, 10, 'pcs', 15.50, 'EUR', 1, 3),
    (3, 20, 'pcs', 49.00, 'EUR', 2, 2);

INSERT INTO price_list_entry_entity (ple_id, price, currency, product_id) VALUES
    (1, 29.99, 'EUR', 1),
    (2, 49.00, 'EUR', 2),
    (3, 15.50, 'EUR', 3);

ALTER TABLE delivery_address_entity ALTER COLUMN id RESTART WITH 10;
ALTER TABLE customer_entity ALTER COLUMN customer_id RESTART WITH 10;
ALTER TABLE product_entity ALTER COLUMN product_number RESTART WITH 10;
ALTER TABLE order_entity ALTER COLUMN order_id RESTART WITH 10;
ALTER TABLE order_line_entity ALTER COLUMN line_number RESTART WITH 10;
ALTER TABLE price_list_entry_entity ALTER COLUMN ple_id RESTART WITH 10;
