CREATE DATABASE `pizzashop`;

USE `pizzashop`;

CREATE TABLE `users` (
                         `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         `username` VARCHAR(255) NOT NULL,
                         `password` VARCHAR(255) NOT NULL,
                         `phone_number` VARCHAR(255) NOT NULL,
                         `email` VARCHAR(255) NOT NULL,
                         `image` VARCHAR(255) NOT NULL,
                         `user_type` VARBINARY(255) NOT NULL
);

CREATE TABLE `customers` (
                             `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
                             `loyalty_points` INT(11) DEFAULT 0
);

CREATE TABLE `employees` (
                             `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
                             `salary` FLOAT(10) DEFAULT 0.0,
                             `fine` FLOAT(10) DEFAULT 0.0
);

CREATE TABLE `products` (
                            `id` INT(100) NOT NULL PRIMARY KEY AUTO_INCREMENT,
                            `name` VARCHAR(100) NOT NULL,
                            `category` ENUM('Sides','Pizzas','Drinks','Meal Deals','Desserts'),
                            `details` VARCHAR(500),
                            `price` FLOAT DEFAULT 0.0,
                            `stock` INT(100) DEFAULT 0,
                            `image` VARCHAR(255)
);

CREATE TABLE `cart` (
                        `id` INT(100) NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        `user_id` INT(100) NOT NULL,
                        `product_id` INT(100) NOT NULL,
                        `order_id` INT(100) NOT NULL,
                        `cost` FLOAT DEFAULT 0.0,
                        `quantity` INT(100) DEFAULT 0,
                        FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
                        FOREIGN KEY (`product_id`) REFERENCES `products`(`id`)
);

CREATE TABLE `review` (
                          `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          `user_id` INT(11) NOT NULL,
                          `comment` VARBINARY(255),
                          `comment_date` DATE,
                          FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
) ;

CREATE TABLE `orders` (
                          `id` INT(100) NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          `product_id` INT(100) NOT NULL,
                          `customer_id` INT(100) NOT NULL,
                          `emp_id` INT(100) NOT NULL,
                          `address` VARCHAR(500) NOT NULL,
                          `payment_method` VARCHAR(50) NOT NULL,
                          `payment_status` ENUM('Pending','Paid') NOT NULL DEFAULT 'Pending',
                          `total_price` FLOAT DEFAULT 0.0,
                          `create_time` DATE,
                          `update_time` DATE,
                          `overdue_time` DATE,
                          `fine` FLOAT(10) DEFAULT 0.0,
                          FOREIGN KEY (`product_id`) REFERENCES `products`(`id`),
                          FOREIGN KEY (`customer_id`) REFERENCES `customers`(`id`),
                          FOREIGN KEY (`emp_id`) REFERENCES `employees`(`id`)
);
