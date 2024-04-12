CREATE DATABASE `pizza_shop`;

USE `pizza_shop`;

CREATE TABLE `users`
(
    `id`           INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `username`     VARCHAR(255) NOT NULL,
    `password`     VARCHAR(255) NOT NULL,
    `phone_number` VARCHAR(255) NOT NULL,
    `email`        VARCHAR(255) NOT NULL,
    `user_type`    VARCHAR(255) NOT NULL
);

CREATE TABLE `customers`
(
    `id`             INT NOT NULL,
    `loyalty_points` INT DEFAULT 0,
    FOREIGN KEY (`id`) REFERENCES `users` (`id`)
);

CREATE TABLE `employees`
(
    `id`      INT NOT NULL,
    `current_order_count` INT DEFAULT 0,
    `status`  VARCHAR(255) DEFAULT 'Available',
    FOREIGN KEY (`id`) REFERENCES `users` (`id`)
);

CREATE TABLE `products`
(
    `id`       INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`     VARCHAR(255) NOT NULL,
    `category` ENUM('Sides','Pizzas','Drinks','Meal_Deals','Desserts') NOT NULL,
    `details`  VARCHAR(500),
    `price`    DOUBLE DEFAULT 0.0,
    `stock`    INT DEFAULT 0,
    `image`    VARCHAR(255)
);

CREATE TABLE `addresses`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `is_default` TINYINT(1) DEFAULT 0,
    `user_id` INT,
    `street` VARCHAR(255),
    `town` VARCHAR(255),
    `county` VARCHAR(255),
    `eir_code` VARCHAR(255),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `orders` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `customer_id` INT,
    `employee_id` INT,
    `address_id` INT,
    `balance` DOUBLE DEFAULT 0.0,
    `payment_status` VARCHAR(255) NOT NULL DEFAULT 'Pending',
    `status` VARCHAR(255) NOT NULL DEFAULT 'Pending',
    `create_time` DATETIME NOT NULL,
    `update_time` DATETIME,
    FOREIGN KEY (`customer_id`) REFERENCES `customers`(`id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employees`(`id`),
    FOREIGN KEY (`address_id`) REFERENCES `addresses`(`id`)
);

CREATE TABLE `order_items`(
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `order_id` INT,
    `product_id` INT,
    `quantity` INT DEFAULT 0,
    `cost` DOUBLE DEFAULT 0.0,
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`),
    FOREIGN KEY (`product_id`) REFERENCES `products`(`id`)
);

CREATE TRIGGER UpdateBalanceAfterInsert
    AFTER INSERT ON order_items FOR EACH ROW
BEGIN
    UPDATE orders
    SET balance = ROUND(balance + NEW.cost,2)
    WHERE id = NEW.order_id;
END;

CREATE TRIGGER UpdateBalanceAfterUpdate
    AFTER UPDATE ON order_items FOR EACH ROW
BEGIN
    UPDATE orders
    SET balance = ROUND(balance - OLD.cost + NEW.cost,2)
    WHERE id = NEW.order_id;
END;

CREATE TRIGGER UpdateStockAfterInsert
    AFTER INSERT ON order_items FOR EACH ROW
BEGIN
    UPDATE products
    SET stock = stock - NEW.quantity
    WHERE id = NEW.product_id;
END;

CREATE TRIGGER UpdateStockOnOrderCancel
    AFTER UPDATE ON orders FOR EACH ROW
BEGIN
    IF OLD.status != 'Cancelled' AND NEW.status = 'Cancelled' THEN
        UPDATE products p
        JOIN order_items oi ON p.id = oi.product_id
        SET p.stock = p.stock + oi.quantity
        WHERE oi.order_id = NEW.id;
    END IF;
END;

CREATE TRIGGER UpdateLoyaltyPoints
    AFTER UPDATE ON orders FOR EACH ROW
BEGIN
    IF NEW.status = 'Finished' THEN
    UPDATE customers
    SET loyalty_points = loyalty_points + NEW.balance
    WHERE id = NEW.customer_id;
END IF;
END;