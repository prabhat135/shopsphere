-- schema.sql
CREATE DATABASE if not exists fashion_store_db;
USE fashion_store_db;

select * from products;
-- Products table
CREATE TABLE IF NOT EXISTS products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DOUBLE NOT NULL,
    original_price DOUBLE,
    discount INT,
    category ENUM('SUMMER', 'WINTER', 'FORMAL', 'CASUAL', 'TRADITIONAL') NOT NULL,
    gender ENUM('WOMEN', 'MEN', 'KIDS', 'UNISEX') NOT NULL,
    rating DOUBLE DEFAULT 0.0,
    review_count INT DEFAULT 0,
    in_stock BOOLEAN DEFAULT TRUE,
    is_featured BOOLEAN DEFAULT FALSE,
    is_new BOOLEAN DEFAULT TRUE,
    sub_category VARCHAR(100),
    stock_quantity INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_category (category),
    INDEX idx_gender (gender),
    INDEX idx_featured (is_featured),
    INDEX idx_new (is_new),
    INDEX idx_price (price)
);

-- Product sizes (many-to-many)
CREATE TABLE IF NOT EXISTS product_sizes (
    product_id BIGINT,
    size VARCHAR(10),
    PRIMARY KEY (product_id, size),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Product colors (many-to-many)
CREATE TABLE IF NOT EXISTS product_colors (
    product_id BIGINT,
    color VARCHAR(50),
    PRIMARY KEY (product_id, color),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Product images (many-to-many)
CREATE TABLE IF NOT EXISTS product_images (
    product_id BIGINT,
    image_url VARCHAR(500),
    PRIMARY KEY (product_id, image_url),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Insert sample products
INSERT INTO products (name, description, price, original_price, discount, category, gender, rating, review_count, in_stock, is_featured, is_new, sub_category, stock_quantity, created_at) VALUES
('Floral Summer Dress', 'Lightweight floral print dress perfect for summer', 1499.00, 1999.00, 25, 'SUMMER', 'WOMEN', 4.5, 128, true, true, true, 'Dress', 50, NOW()),
('Wool Winter Coat', 'Warm wool coat for winter season', 2999.00, 3999.00, 25, 'WINTER', 'WOMEN', 4.7, 89, true, true, false, 'Coat', 30, NOW()),
('Formal Business Suit', 'Elegant business suit for formal occasions', 4999.00, NULL, NULL, 'FORMAL', 'MEN', 4.3, 156, true, true, true, 'Suit', 40, NOW()),
('Casual Denim Jacket', 'Stylish denim jacket for casual wear', 1999.00, 2499.00, 20, 'CASUAL', 'UNISEX', 4.6, 234, true, true, false, 'Jacket', 60, NOW()),
('Traditional Silk Saree', 'Beautiful silk saree for traditional wear', 3999.00, 4999.00, 20, 'TRADITIONAL', 'WOMEN', 4.8, 178, true, true, true, 'Saree', 25, NOW()),
('Kids Winter Jacket', 'Warm and comfortable jacket for kids', 1299.00, 1699.00, 24, 'WINTER', 'KIDS', 4.4, 67, true, false, true, 'Jacket', 45, NOW()),
('Summer T-Shirt', 'Cotton T-shirt for summer', 799.00, 999.00, 20, 'SUMMER', 'MEN', 4.2, 189, true, false, false, 'T-Shirt', 100, NOW()),
('Formal Leather Shoes', 'Premium leather shoes for formal wear', 2499.00, 2999.00, 17, 'FORMAL', 'MEN', 4.5, 123, true, true, true, 'Shoes', 35, NOW());

-- Insert sizes for products
INSERT INTO product_sizes (product_id, size) VALUES
(1, 'S'), (1, 'M'), (1, 'L'), (1, 'XL'),
(2, 'M'), (2, 'L'), (2, 'XL'),
(3, '38'), (3, '40'), (3, '42'), (3, '44'),
(4, 'S'), (4, 'M'), (4, 'L'), (4, 'XL'),
(5, 'One Size'),
(6, 'S'), (6, 'M'), (6, 'L'),
(7, 'M'), (7, 'L'), (7, 'XL'), (7, 'XXL'),
(8, '8'), (8, '9'), (8, '10'), (8, '11');

-- Insert colors for products
INSERT INTO product_colors (product_id, color) VALUES
(1, 'Pink'), (1, 'White'), (1, 'Blue'),
(2, 'Black'), (2, 'Navy Blue'), (2, 'Grey'),
(3, 'Black'), (3, 'Navy Blue'), (3, 'Charcoal'),
(4, 'Blue'), (4, 'Black'), (4, 'Grey'),
(5, 'Red'), (5, 'Green'), (5, 'Blue'),
(6, 'Red'), (6, 'Blue'), (6, 'Yellow'),
(7, 'White'), (7, 'Black'), (7, 'Grey'),
(8, 'Black'), (8, 'Brown');

-- Insert images for products
INSERT INTO product_images (product_id, image_url) VALUES
(1, 'https://assets.myntassets.com/dpr_1.5,q_30,w_400,c_limit,fl_progressive/assets/images/27443692/2024/12/6/2148e1e4-af7f-45e9-a6c2-501c743209471733477391151-Azira-Checked-Cotton-Empire-Puff-Sleeve-Layered-Fit--Flare-M-1.jpg'),
(1, 'https://m.media-amazon.com/images/I/81ZR+jA48VL._AC_UY1100_.jpg'),
(2, 'https://sheforstyle.com/wp-content/uploads/2024/08/Long-Coat-Outfits-21.jpg'),
(2, 'https://assets.myntassets.com/dpr_1.5,q_30,w_400,c_limit,fl_progressive/assets/images/2024/SEPTEMBER/25/n4AaLXy2_6f7d5d0db6ed4f7a93f825b6035aefd1.jpg'),
(3, 'https://imagescdn.peterengland.com/img/app/product/9/937829-11975818.jpg?auto=format&w=390'),
(3, 'https://imagescdn.louisphilippe.com/img/app/product/3/39824745-19241127.jpg?auto=format&w=390'),
(4, 'https://m.media-amazon.com/images/I/61A35FdLX9L._AC_UY1100_.jpg'),
(4, 'https://assets.myntassets.com/w_412,q_30,dpr_3,fl_progressive,f_webp/assets/images/32475124/2025/6/20/fd3dcb3b-de80-4352-b4d9-d422780040821750414569819-Bene-Kleed-Men-Spread-Collar-Graphic-Printed-Casual-Denim-Ja-1.jpg'),
(5, 'https://zardozipune.com/wp-content/uploads/2024/04/A-31-scaled.jpg'),
(5, 'https://wholetex.sgp1.cdn.digitaloceanspaces.com/full/wlnath-paithani-soft-silk-saree-203.jpg'),
(6, 'https://www.akelion.co.in/cdn/shop/files/Winterwear-Highly_Insulated_Olive_Green_Winter_Jacket_for_Kids.png?v=1758366253'),
(6, 'https://img4.dhresource.com/webp/m/0x0/f3/albu/km/z/19/9d551c3f-0358-4dd6-820c-7b95161db7a2.JPG'),
(7, 'https://cottonworld.net/cdn/shop/files/M-JACKETS-17563-21286-NATURAL_1.jpg?v=1753678334&width=1920'),
(7, 'https://m.media-amazon.com/images/I/61sK101PGAL._AC_UY1100_.jpg');
