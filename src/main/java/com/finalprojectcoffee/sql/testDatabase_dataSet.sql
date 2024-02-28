USE testpizzashop;

INSERT INTO `users` (`id`, `username`, `password`, `phone_number`, `email`, `image`,`user_type`) VALUES
    (1, 'cookregina', '_IfkP$*t@4', '311-325-1356', 'aguilarsara@hotmail.com', NULL, 'Customer'),
    (2, 'joseph40', 'kDk3(mDr_5', '+1-442-410-1111x483', 'peter71@perez.org', NULL, 'Employee'),
    (3, 'sherriryan', '$8Ma7AGyZj', '(842)795-0469', 'longkyle@smith-white.org', NULL, 'Customer'),
    (4, 'joshua59', '(@V$^kKi38', '+1-991-783-5926x612', 'gcooper@hotmail.com', NULL, 'Employee'),
    (5, 'cgarza', '9%5DtVupi&', '+1-026-380-7135x026', 'snyderjames@mays.info', NULL, 'Customer'),
    (6, 'jeremy56', 'Om3JQGKd!U', '485.559.3441', 'jackhenderson@foster.info', NULL, 'Employee'),
    (7, 'davismary', 'RWz2HABr$1', '001-646-753-6484', 'bennettmolly@yahoo.com', NULL, 'Customer'),
    (8, 'kanestacy', 't$@j6GJlRa', '+1-628-261-8054x372', 'whitejessica@christensen.com', NULL, 'Employee'),
    (9, 'chase96', '+LXO9Vf+A0', '467.778.7273', 'debbie44@williams-mann.com', NULL, 'Customer'),
    (10, 'oknapp', '8xs3aL@l)6', '320.589.0644x186', 'olivia36@hotmail.com', NULL, 'Employee');

INSERT INTO `customers` (`id`, `loyalty_points`) VALUES
    (1, 89),
    (3, 786),
    (5, 317),
    (7, 763),
    (9, 589);

INSERT INTO `employees` (`id`, `salary`) VALUES
    (2, 43694.26),
    (4, 32659.36),
    (6, 52066.96),
    (8, 53275.26),
    (10, 34917.29);

INSERT INTO `products` (`id`, `name`, `category`, `details`, `price`, `stock`, `image`) VALUES
    (1, 'Pepperoni Deluxe', 'Pizzas', 'Pepperoni and Cheese 10`', 5.99, 100, 'pizza1.jpg'),
    (2, 'Pepperoni Family Meal Deal', 'Meal_Deals', '5 Pizzas and Kids Meal Included', 18, 100, 'mealdeal.png');

INSERT INTO `carts` (`id`, `total_cost`) VALUES
    (1, 47.95),
    (2, 59.96),
    (3, 23.96);

INSERT INTO `cart_items` (`id`, `cart_id`, `product_id`, `quantity`, `cost`) VALUES
    (1, 1, 1, 2, 11.98),
    (2, 1, 2, 1, 18.00),
    (3, 1, 1, 3, 17.97),
    (4, 2, 2, 2, 36.00),
    (5, 2, 1, 4, 23.96),
    (6, 3, 1, 4, 23.96);

INSERT INTO `orders` (`cart_id`, `customer_id`, `temp_address_id`, `balance`, `payment_status`, `status`, `create_time`) VALUES
    (1, 1, null, 47.95,  'Pending', 'Pending', '2024-02-01'),
    (2, 3, null, 0.0, 'Paid', 'Delivered', '2024-02-05');

INSERT INTO `temp_addresses`(`id`, `street`, `town`, `county`, `eir_code`) VALUES
    (1, 'Dunblin Road', 'Dundalk', 'Louth', 'A91DC99');

ALTER TABLE `cart_items`
    ADD CONSTRAINT `fk_cart_items_carts`
        FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE;
