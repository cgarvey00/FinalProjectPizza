CREATE
DATABASE `pizzashop`;

USE
`pizzashop`;

CREATE TABLE `users`
(
    `id`           INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
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
    `id`     INT NOT NULL,
    `salary` DOUBLE DEFAULT 0.0,
    `status` VARCHAR(255),
    FOREIGN KEY (`id`) REFERENCES `users` (`id`)
);

CREATE TABLE `products`
(
    `id`       INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name`     VARCHAR(255) NOT NULL,
    `category` ENUM('Sides','Specials','Pizzas','Drinks','Meal_Deals','Desserts'),
    `details`  VARCHAR(500),
    `price`    DOUBLE DEFAULT 0.0,
    `stock`    INT    DEFAULT 0,
    `image`    VARCHAR(255)
);

CREATE TABLE `carts`
(
    `id`          INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `customer_id` INT NOT NULL,
    `product_id`  INT NOT NULL,
    `quantity`    INT    DEFAULT 0,
    `total_cost`  DOUBLE DEFAULT 0.0,
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
);

CREATE TABLE `review`
(
    `id`           INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `user_id`      INT          NOT NULL,
    `comment`      VARCHAR(255),
    `comment_date` DATE,
    `stars`        INT,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `addresses`
(
    `id`       INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id`  INT NOT NULL,
    `street`   VARCHAR(255),
    `town`     VARCHAR(255),
    `county`   VARCHAR(255),
    `eir_code` VARCHAR(255),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `orders`
(
    `id`             INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `customer_id`    INT          NOT NULL,
    `employee_id`    INT,
    `address_id`     INT          NOT NULL,
    `balance`        DOUBLE                DEFAULT 0.0,
    `payment_status` VARCHAR(255) NOT NULL DEFAULT 'Pending',
    `status`         VARCHAR(255) NOT NULL DEFAULT 'Pending',
    `create_time`    DATE         NOT NULL,
    `update_time`    DATE,
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
    FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
    FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`)
);

CREATE TABLE `order_items`
(
    `id`         INT    NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `order_id`   INT    NOT NULL,
    `product_id` INT    NOT NULL,
    `quantity`   INT    NOT NULL,
    `cost`       DOUBLE NOT NULL,
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
);

INSERT INTO `products` (`id`, `name`, `category`, `details`, `price`, `stock`, `image`)
VALUES (1, 'Pepperoni Deluxe', 'Pizzas', 'Pepperoni and Cheese 10`', 5.99, 230, 'pizza1.jpg'),
       (2, 'Pepperoni Family Meal Deal', 'Meal_Deals', '5 Pizzas and Kids Meal Included', 16, 100, 'mealdeal.png'),
       (3, 'Hawaiian Pizza', 'Pizzas', 'Ham, Pineapple, and Cheese', 7.99, 160, 'hawaiian_pizza.jpg'),
       (4, 'Chocolate Chip Ice-Cream', 'Desserts', 'Deluxe Chocolate Chip Ice Cream', 1, 300, 'icecream.jpg'),
       (5, 'BBQ Chicken Pizza', 'Pizzas', 'Grilled Chicken, BBQ Sauce, and Red Onions', 9.49, 200,
        'bbq_chicken_pizza.jpg'),
       (6, 'Family Feast Meal Deal', 'Meal_Deals', '3 Pizzas, Pasta, Salad, and Dessert', 35.99, 300,
        'family_feast_mealdeal.jpg'),
       (7, 'Margherita Pizza', 'Pizzas', 'Tomato, Mozzarella, and Basil', 7.99, 200, 'margherita.jpg'),
       (8, 'Strawberry Cheesecake', 'Desserts', 'Creamy Cheesecake with Strawberry Topping', 9.99, 0,
        'strawberry_cheesecake.jpg'),
       (9, 'Combo Meal for Four', 'Meal_Deals', '2 Pizzas, Pasta, Salad, and Dessert', 29.99, 30,
        'combo_meal_for_four.jpg'),
       (10, 'Deluxe Party Pack', 'Meal_Deals', 'Variety of Pizzas, Sides, and Drinks', 49.99, 20,
        'deluxe_party_pack.jpg'),
       (11, 'Vegetarian Combo', 'Meal_Deals', 'Veggie Pizza, Salad, and Fruit Smoothie', 16.99, 40,
        'vegetarian_combo.jpg'),
       (12, 'Coca Cola', 'Drinks', 'Classic Cola Soda', 1, 100, 'cola.jpg'),
       (13, 'Diet Coke', 'Drinks', 'Diet Cola Soda', 1, 100, 'dietcola.jpg'),
       (14, 'Fanta', 'Drinks', 'Fanta Orange', 1, 100, 'fanta.jpg'),
       (15, 'Z-Up', 'Drinks', '7-UP Lemon', 1, 100, 'lemon.jpg'),
       (16, 'Chocolate Milkshake', 'Drinks', 'Galaxy chocolate Milkshake', 2, 400, 'shake.png');


INSERT INTO `users` (`id`, `username`, `password`, `phone_number`, `email`, `image`, `user_type`)
VALUES (1, 'adminUser', '$2a$10$hW5VT1ipIDSm7jCC68q37.dezGqJdqJfxAce10zQrfH9HdtWHCipi', '4456433567',
        'admin2@outlook.com', NULL, 'Admin'),
       (2, 'cgarvey00', '$2a$10$0TXXP.0Otetx9tyjRfPNUe5De5Z6SKvbXPL/rVUljqMC/W8Xj0g5S', '3565436789',
        'conorgarvey27@gmail.com', 'nulle', 'Customer'),
       (3, 'newUser', '$2a$10$PYM0WN4m2Fji.oRd06nB0ucmX40.B4Vi8P9yj0RbKf/pOHyEwg06u', '1239393939', 'cmail@mail.com',
        NULL, 'Employee');

INSERT INTO `employees` (`id`, `salary`, `status`)
VALUES (3, NULL, NULL);
INSERT INTO `customers` (`id`, `loyalty_points`)
VALUES (2, NULL);


