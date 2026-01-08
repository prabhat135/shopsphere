-- Insert sample users
INSERT INTO users (email, password, first_name, last_name, role, enabled) VALUES
('admin@shopsphere.com', '$2a$10$yourHashedPasswordHere', 'Admin', 'User', 'ADMIN', true),
('user@shopsphere.com', '$2a$10$yourHashedPasswordHere', 'Regular', 'User', 'USER', true);

-- Insert sample products
INSERT INTO products (name, description, price, original_price, discount, category, gender, rating, review_count, stock_quantity, featured, is_new, sub_category) VALUES
('Floral Summer Dress', 'Lightweight floral print dress perfect for summer', 1499.00, 1999.00, 25, 'SUMMER', 'WOMEN', 4.5, 128, 100, true, true, 'Dress'),
('Denim Jacket', 'Classic blue denim jacket for casual wear', 2499.00, NULL, NULL, 'CASUAL', 'UNISEX', 4.2, 89, 75, true, false, 'Jacket'),
('Formal Suit', 'Premium wool blend suit for formal occasions', 5999.00, 6999.00, 14, 'FORMAL', 'MEN', 4.8, 45, 50, true, true, 'Suit'),
('Winter Coat', 'Warm wool coat for winter season', 3999.00, 4999.00, 20, 'WINTER', 'WOMEN', 4.3, 67, 60, true, false, 'Coat'),
('Traditional Kurta', 'Embroidered cotton kurta for festivals', 1899.00, NULL, NULL, 'TRADITIONAL', 'MEN', 4.6, 112, 120, false, true, 'Kurta'),
('Kids T-Shirt', 'Colorful cartoon print t-shirt for kids', 499.00, 699.00, 28, 'CASUAL', 'KIDS', 4.1, 34, 200, false, true, 'T-Shirt');

-- Insert sizes
INSERT INTO product_sizes (product_id, size) VALUES
(1, 'S'), (1, 'M'), (1, 'L'), (1, 'XL'),
(2, 'XS'), (2, 'S'), (2, 'M'), (2, 'L'), (2, 'XL'),
(3, '38'), (3, '40'), (3, '42'), (3, '44'),
(4, 'S'), (4, 'M'), (4, 'L'),
(5, 'M'), (5, 'L'), (5, 'XL'),
(6, '4-5Y'), (6, '6-7Y'), (6, '8-9Y');

-- Insert colors
INSERT INTO product_colors (product_id, color) VALUES
(1, 'Pink'), (1, 'White'), (1, 'Blue'),
(2, 'Blue'), (2, 'Black'),
(3, 'Black'), (3, 'Navy'), (3, 'Grey'),
(4, 'Red'), (4, 'Black'), (4, 'Beige'),
(5, 'White'), (5, 'Blue'), (5, 'Maroon'),
(6, 'Red'), (6, 'Blue'), (6, 'Green'), (6, 'Yellow');

-- Insert images
INSERT INTO product_images (product_id, image_url) VALUES
(1, 'products/women/summer/dress1.jpg'),
(1, 'products/women/summer/dress2.jpg'),
(2, 'products/unisex/casual/jacket1.jpg'),
(3, 'products/men/formal/suit1.jpg'),
(4, 'products/women/winter/coat1.jpg'),
(5, 'products/men/traditional/kurta1.jpg'),
(6, 'products/kids/casual/tshirt1.jpg');