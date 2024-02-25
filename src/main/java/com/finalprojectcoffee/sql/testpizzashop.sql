CREATE DATABASE `testpizzashop`;

USE `testpizzashop`;

CREATE TABLE `users`
(
    `id`           INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `username`     VARCHAR(255) NOT NULL,
    `password`     VARCHAR(255) NOT NULL,
    `phone_number` VARCHAR(255) NOT NULL,
    `email`        VARCHAR(255) NOT NULL,
    `image`        VARCHAR(255),
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
    `salary`  DOUBLE DEFAULT 0.0,
    `status`  VARCHAR(255),
    FOREIGN KEY (`id`) REFERENCES `users` (`id`)
);

CREATE TABLE `products`
(
    `id`       INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`     VARCHAR(255) NOT NULL,
    `category` ENUM('Sides','Pizzas','Drinks','Meal_Deals','Desserts'),
    `details`  VARCHAR(500),
    `price`    DOUBLE DEFAULT 0.0,
    `stock`    INT DEFAULT 0,
    `image`    VARCHAR(255)
);

CREATE TABLE `carts` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `order_id` INT NOT NULL,
    `cart_item_id` INT,
    `total_cost` DOUBLE DEFAULT 0.0,
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`),
    FOREIGN KEY (`cart_item_id`) REFERENCES `cart_items`(`id`)
);

CREATE TABLE `cart_items`(
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `product_id` INT NOT NULL,
    `quantity` INT DEFAULT 0,
    `cost` DOUBLE DEFAULT 0.0,
    FOREIGN KEY (`product_id`) REFERENCES `products`(`id`)
);

CREATE TABLE `review` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `comment` VARCHAR(255),
    `comment_date` DATE,
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
) ;

CREATE TABLE `address`(
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `street` VARCHAR(255),
    `town` VARCHAR(255),
    `county` VARCHAR(255),
    `eir_code` VARCHAR(255),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `temp_addresses` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `street` VARCHAR(255),
    `town` VARCHAR(255),
    `county` VARCHAR(255),
    `eir_code` VARCHAR(255)
);

CREATE TABLE `orders` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `cart_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `temp_address_id` INT,
    `balance` DOUBLE DEFAULT 0.0,
    `payment_method` VARCHAR(255) NOT NULL,
    `payment_status` VARCHAR(255) NOT NULL DEFAULT 'Pending',
    `status` VARCHAR(255) NOT NULL,
    `create_time` DATE,
    `update_time` DATE,
    `overdue_time` DATE,
    FOREIGN KEY (`cart_id`) REFERENCES `cart`(`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
    FOREIGN KEY (`temp_address_id`) REFERENCES `temp_addresses`(`id`)
);