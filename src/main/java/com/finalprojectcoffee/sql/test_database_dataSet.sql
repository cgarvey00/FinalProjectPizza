USE test_pizza_shop;

INSERT INTO `users` (`id`, `username`, `password`, `phone_number`, `email`, `user_type`) VALUES
    (1, 'admin', '_IfkP$*t@4', '311-325-1356', 'aguilarsara@hotmail.com', 'Admin'),
    (2, 'joseph40', 'kDk3(mDr_5', '+1-442-410-1111x483', 'peter71@perez.org', 'Employee'),
    (3, 'test', '$8Ma7AGyZj', '(842)795-0469', 'longkyle@smith-white.org', 'Customer'),
    (4, 'joshua59', '(@V$^kKi38', '+1-991-783-5926x612', 'gcooper@hotmail.com',  'Employee'),
    (5, 'cgarza', '9%5DtVupi&', '+1-026-380-7135x026', 'snyderjames@mays.info', 'Customer'),
    (6, 'jeremy56', 'Om3JQGKd!U', '485.559.3441', 'jackhenderson@foster.info', 'Employee'),
    (7, 'davismary', 'RWz2HABr$1', '001-646-753-6484', 'bennettmolly@yahoo.com', 'Customer'),
    (8, 'kanestacy', 't$@j6GJlRa', '+1-628-261-8054x372', 'whitejessica@christensen.com', 'Employee'),
    (9, 'chase96', '+LXO9Vf+A0', '467.778.7273', 'debbie44@williams-mann.com', 'Customer'),
    (10, 'oknapp', '8xs3aL@l)6', '320.589.0644x186', 'olivia36@hotmail.com', 'Employee');

INSERT INTO `customers` (`id`) VALUES
    (3),
    (5),
    (7),
    (9);

INSERT INTO `employees` (`id`) VALUES
    (2),
    (4),
    (6),
    (8),
    (10);

INSERT INTO `products` (`id`, `name`, `category`, `details`, `price`, `stock`, `image`) VALUES
    (1, 'Pepperoni Deluxe', 'Pizzas', 'Pepperoni and Cheese 10`', 5.99, 100, 'PepperoniDeluxe.png'),
    (2, 'Pepperoni Family Meal Deal', 'Meal_Deals', '5 Pizzas and Kids Meal Included', 18, 100, 'PepperoniFamilyMealDeal.png');

INSERT INTO `addresses`(`id`, `is_default`, `user_id`, `street`, `town`, `county`, `eir_code`) VALUES
    (1, 1, 3, 'Dublin Road', 'Dundalk', 'Louth', 'DT123F2'),
    (2, 0, 3, 'Dublin Road', 'Dundalk', 'Louth', 'GEI422P'),
    (3, 1, 5, 'Dublin Road', 'Dundalk', 'Louth', 'A91DC99');

INSERT INTO `orders` (`id`, `customer_id`, `employee_id`, `address_id`, `balance`, `payment_status`, `status`, `create_time`, `update_time`) VALUES
    (1, 3, 2, 1, 47.95, 'Pending', 'Pending', '2024-04-16', '2024-4-16'),
    (2, 5, 8, 3, 24.0, 'Paid', 'Pending', '2024-04-15', '2024-4-15');

INSERT INTO `order_items` (`id`, `order_id`, `product_id`, `quantity`, `cost`) VALUES
    (1, 1, 1, 2, 11.98),
    (2, 1, 2, 1, 18.00),
    (3, 1, 1, 3, 17.97),
    (4, 2, 2, 2, 36.00),
    (5, 2, 1, 4, 23.96);