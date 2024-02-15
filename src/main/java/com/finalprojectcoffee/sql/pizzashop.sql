CREATE DATABASE `pizzashop`;

USE `pizzashop`;

CREATE TABLE `address`(
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT(11) NOT NULL,
    `street` VARCHAR(255),
    `town` VARCHAR(255),
    `county` VARCHAR(255),
    `eir_code` VARCHAR(255),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `users`
(
    `id`           INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `username`     VARCHAR(255) NOT NULL,
    `password`     VARCHAR(255) NOT NULL,
    `phone_number` VARCHAR(255) NOT NULL,
    `email`        VARCHAR(255) NOT NULL,
    `image`        VARCHAR(255),
    `type`         VARCHAR(255) NOT NULL
);

CREATE TABLE `customers`
(
    `id`             INT(11) NOT NULL,
    `loyalty_points` INT(11) DEFAULT 0,
    FOREIGN KEY (`id`) REFERENCES `users` (`id`)
);

CREATE TABLE `employees`
(
    `id`      INT(11) NOT NULL,
    `salary`  FLOAT(10) DEFAULT 0.0,
    FOREIGN KEY (`id`) REFERENCES `users` (`id`)
);

CREATE TABLE `products`
(
    `id`       INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`     VARCHAR(255) NOT NULL,
    `category` ENUM('Sides','Pizzas','Drinks','Meal_Deals','Desserts'),
    `details`  VARCHAR(500),
    `price`    FLOAT DEFAULT 0.0,
    `stock`    INT(100) DEFAULT 0,
    `image`    VARCHAR(255)
);

CREATE TABLE `cart` (
    `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT(11) NOT NULL,
    `product_id` INT(11) NOT NULL,
    `cost` FLOAT DEFAULT 0.0,
    `quantity` INT(100) DEFAULT 0,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
    FOREIGN KEY (`product_id`) REFERENCES `products`(`id`)
);

CREATE TABLE `review` (
    `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT(11) NOT NULL,
    `comment` VARCHAR(255),
    `comment_date` DATE,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
) ;

CREATE TABLE `orders` (
    `id` INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `product_id` INT(11) NOT NULL,
    `customer_id` INT(11) NOT NULL,
    `employee_id` INT(11) NOT NULL,
    `temp_address_id` INT(11),
    `payment_method` VARCHAR(50) NOT NULL,
    `payment_status` ENUM('Pending','Paid') NOT NULL DEFAULT 'Pending',
    `total_price` FLOAT DEFAULT 0.0,
    `create_time` DATE,
    `update_time` DATE,
    `overdue_time` DATE,
    `fine` FLOAT(10) DEFAULT 0.0,
    FOREIGN KEY (`product_id`) REFERENCES `products`(`id`),
    FOREIGN KEY (`customer_id`) REFERENCES `customers`(`id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employees`(`id`),
    FOREIGN KEY (`temp_address_id`) REFERENCES `temp_addresses`(`id`)
);

CREATE TABLE `temp_addresses` (
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `street` VARCHAR(255),
    `town` VARCHAR(255),
    `county` VARCHAR(255),
    `eir_code` VARCHAR(255)
);