--
-- Database: `pizzashop5`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddCustomerOrder` (IN `p_customer_id` INT, IN `p_address_id` INT, IN `p_product_id` INT, IN `p_quantity` INT, IN `p_unit_price` DOUBLE)   BEGIN
    DECLARE v_total_cost DOUBLE DEFAULT p_quantity * p_unit_price;
    DECLARE v_order_id INT;

START TRANSACTION;

INSERT INTO orders(customer_id,address_id,balance,payment_status,status,create_time) VALUES (p_customer_id,p_address_id,v_total_cost,'Pending','Pending',CURDATE());

SET v_order_id=LAST_INSERT_ID();

INSERT INTO order_items(order_id,product_id,quantity,cost)
VALUES(v_order_id,p_product_id,p_quantity,v_total_cost);

UPDATE products
SET stock=stock-p_quantity
WHERE id=p_product_id;

COMMIT;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ArchivePastOrders` ()   BEGIN 
	  DECLARE cut_off_date DATE;
      SET cut_off_date = CURDATE()-INTERVAL 1 YEAR;

INSERT INTO orders_archive
SELECT * FROM orders_archive
WHERE create_time < cut_off_date;

INSERT INTO order_items_archive
SELECT * FROM order_items
WHERE order_id IN (SELECT id FROM orders WHERE create_time < cut_off_date);

# DELETE FROM order_items WHERE order_id IN (SELECT id FROM orders WHERE create_items<cut_off_date);

DELETE FROM order_items WHERE order_id IN (SELECT id FROM orders WHERE create_time< cut_off_date);
DELETE FROM orders WHERE create_time<cut_off_date;
END$$

--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `CalculateTotalOrderCost` (`order_id` INT) RETURNS DOUBLE  BEGIN
    DECLARE total_cost DOUBLE DEFAULT 0;

SELECT SUM(cost) INTO total_cost
FROM order_items
WHERE order_id = order_id;

RETURN total_cost;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `addresses`
--

CREATE TABLE `addresses` (
                             `id` int(11) NOT NULL,
                             `user_id` int(11) NOT NULL,
                             `street` varchar(255) DEFAULT NULL,
                             `town` varchar(255) DEFAULT NULL,
                             `county` varchar(255) DEFAULT NULL,
                             `eir_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `addresses`
--

INSERT INTO `addresses` (`id`, `user_id`, `street`, `town`, `county`, `eir_code`) VALUES
                                                                                      (1, 2, 'New street', 'NewTown', 'Louth', '22H-22LL'),
                                                                                      (2, 2, 'New Street 2', 'Ana', 'Meath', 'SSS-E2222'),
                                                                                      (10, 2, 'my Address', 'Blackrock', 'Louth', 'SSS 3333S'),
                                                                                      (15, 9, 'SSSS', 'SSSSS', 'Louth', 'DDD3 4444'),
                                                                                      (16, 9, '27 Castle Road', ' Dundalk', 'Co. Louth', 'A91 X9D7'),
                                                                                      (17, 9, 'Lios Dubh Community Centre, Lios Dubh, Armagh Rd, Lisdoo', 'Dundalk', 'Louth', 'A91 T668'),
                                                                                      (18, 10, '20 Newry Road', 'Dundalk', 'Co. Louth', 'A91 Y3X8');

-- --------------------------------------------------------

--
-- Table structure for table `carts`
--

CREATE TABLE `carts` (
                         `id` int(11) NOT NULL,
                         `total_cost` double DEFAULT 0,
                         `quantity` int(11) NOT NULL,
                         `customer_id` int(11) NOT NULL,
                         `product_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `carts`
--

INSERT INTO `carts` (`id`, `total_cost`, `quantity`, `customer_id`, `product_id`) VALUES
                                                                                      (51, 71.98, 2, 10, 6),
                                                                                      (52, 29.99, 1, 10, 9),
                                                                                      (54, 7.99, 1, 2, 3);

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
                             `id` int(11) NOT NULL,
                             `loyalty_points` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`id`, `loyalty_points`) VALUES
                                                     (2, 10),
                                                     (3, 0),
                                                     (5, 0),
                                                     (6, 0),
                                                     (8, NULL),
                                                     (9, NULL),
                                                     (10, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
                             `id` int(11) NOT NULL,
                             `salary` double DEFAULT 0,
                             `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`id`, `salary`, `status`) VALUES
    (4, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
                          `id` int(11) NOT NULL,
                          `customer_id` int(11) NOT NULL,
                          `employee_id` int(11) DEFAULT NULL,
                          `address_id` int(11) NOT NULL,
                          `balance` double DEFAULT 0,
                          `payment_status` varchar(255) NOT NULL DEFAULT 'Pending',
                          `status` varchar(255) NOT NULL DEFAULT 'Pending',
                          `create_time` date NOT NULL,
                          `update_time` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `customer_id`, `employee_id`, `address_id`, `balance`, `payment_status`, `status`, `create_time`, `update_time`) VALUES
                                                                                                                                                 (1, 2, 4, 10, 45.99, 'Paid', 'Delivered', '2024-03-19', '2024-04-26'),
                                                                                                                                                 (2, 2, 4, 1, 39.48, 'Paid', 'Delivered', '2024-03-19', '2024-04-26'),
                                                                                                                                                 (3, 2, 4, 1, 2, 'Paid', 'Delivered', '2024-04-22', '2024-04-26'),
                                                                                                                                                 (4, 2, 4, 2, 2, 'Paid', 'Delivered', '2024-04-24', '2024-04-26'),
                                                                                                                                                 (5, 2, 4, 1, 4, 'Paid', 'Delivered', '2024-04-24', '2024-04-26');

--
-- Triggers `orders`
--
DELIMITER $$
CREATE TRIGGER `IncreaseStock` AFTER UPDATE ON `orders` FOR EACH ROW BEGIN
    IF NEW.status='Cancelled' AND OLD.status !='Cancelled' THEN
    UPDATE products p
        INNER JOIN order_items oi ON p.id=oi.product_id
        SET p.stock=p.stock +oi.quantity
    WHERE oi.order_id=NEW.id;
END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `UpdateLoyaltyPoints` AFTER INSERT ON `orders` FOR EACH ROW BEGIN
    UPDATE customers
    SET loyalty_points = loyalty_points + 10
    WHERE id = NEW.customer_id;
END
    $$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `ordersummary`
-- (See below for the actual view)
--
CREATE TABLE `ordersummary` (
                                `OrderID` int(11)
    ,`Customer` varchar(255)
    ,`TotalItems` decimal(32,0)
    ,`TotalCost` double
);

-- --------------------------------------------------------

--
-- Table structure for table `orders_archive`
--

CREATE TABLE `orders_archive` (
                                  `id` int(11) NOT NULL,
                                  `customer_id` int(11) NOT NULL,
                                  `employee_id` int(11) DEFAULT NULL,
                                  `address_id` int(11) NOT NULL,
                                  `balance` double DEFAULT 0,
                                  `payment_status` varchar(255) NOT NULL DEFAULT 'Pending',
                                  `status` varchar(255) NOT NULL DEFAULT 'Pending',
                                  `create_time` date NOT NULL,
                                  `update_time` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
                               `id` int(11) NOT NULL,
                               `order_id` int(11) NOT NULL,
                               `product_id` int(11) NOT NULL,
                               `quantity` int(11) NOT NULL,
                               `cost` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`id`, `order_id`, `product_id`, `quantity`, `cost`) VALUES
                                                                                   (1, 1, 2, 1, 16),
                                                                                   (2, 1, 9, 1, 29.99),
                                                                                   (3, 2, 5, 1, 9.49),
                                                                                   (4, 2, 9, 1, 29.99),
                                                                                   (5, 3, 17, 1, 2),
                                                                                   (6, 4, 17, 1, 2),
                                                                                   (7, 5, 18, 2, 4);

--
-- Triggers `order_items`
--
DELIMITER $$
CREATE TRIGGER `DecreaseStock` AFTER INSERT ON `order_items` FOR EACH ROW BEGIN
    UPDATE products SET stock=stock-NEW.quantity
    WHERE id=NEW.product_id;
END
    $$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `order_items_archive`
--

CREATE TABLE `order_items_archive` (
                                       `id` int(11) NOT NULL,
                                       `order_id` int(11) NOT NULL,
                                       `product_id` int(11) NOT NULL,
                                       `quantity` int(11) NOT NULL,
                                       `cost` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
                            `id` int(11) NOT NULL,
                            `name` varchar(255) NOT NULL,
                            `category` enum('Sides','Pizzas','Drinks','Meal_Deals','Desserts','Specials') DEFAULT NULL,
                            `details` varchar(500) DEFAULT NULL,
                            `price` double DEFAULT 0,
                            `stock` int(11) DEFAULT 0,
                            `image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `category`, `details`, `price`, `stock`, `image`) VALUES
                                                                                            (1, 'Pepperoni Deluxe', 'Pizzas', 'Pepperoni and Cheese 10`', 5.99, 230, '69074450-7a05-467d-a1a8-8aebb100acb0.jpg'),
                                                                                            (2, 'Pepperoni Family Meal Deal', 'Meal_Deals', '5 Pizzas and Kids Meal Included', 16, 1000, '6d8c7fb0-18e2-4a4e-ac67-abb14988e51a.jpg'),
                                                                                            (3, 'Hawaiian Pizza', 'Pizzas', 'Ham, Pineapple, and Cheese', 7.99, 210, 'ff8c201e-24b8-44eb-a501-789f3c041ccb.jpg'),
                                                                                            (4, 'Chocolate Chip Ice-Cream', 'Desserts', 'Deluxe Chocolate Chip Ice Cream', 1, 1000, '6e130699-cff9-4e6e-966d-508cf9e69193.jpg'),
                                                                                            (5, 'BBQ Chicken Pizza', 'Pizzas', 'Grilled Chicken, BBQ Sauce, and Red Onions', 5.9, 1000, '2ec1b989-b44c-452e-93de-aad6a47c764d.jpg'),
                                                                                            (6, 'Family Feast Meal Deal', 'Meal_Deals', '3 Pizzas, Pasta, Salad, and Dessert', 35.99, 300, '9a6e15d5-d53e-4718-a5dc-abe585c1d72f.jpg'),
                                                                                            (7, 'Margherita Pizza', 'Pizzas', 'Tomato, Mozzarella, and Basil', 7.99, 1000, '88d0e958-1032-4c64-b23e-e0569830288f.jpg'),
                                                                                            (8, 'Strawberry Cheesecake', 'Desserts', 'Creamy Cheesecake with Strawberry Topping', 9.99, 0, '2109f717-1cf4-4c2a-8ef4-724fa7916235.jpg'),
                                                                                            (9, 'Combo Meal for Four', 'Meal_Deals', '14\' Pizza,  Salad, and a 2L Drink of Choice', 29.99, 30, '9ff04e76-7e8c-46ad-8ff5-29954091e280.jpg'),
(10, 'Deluxe Party Pack', 'Meal_Deals', 'Variety of Pizzas, Sides, and Drinks', 49.99, 20, 'a83d819c-eb3e-4ea9-9c4d-d41a95ff0d1b.jpg'),
(11, 'Vegetarian Combo', 'Meal_Deals', 'Veggie Pizza, Salad, and Fruit Smoothie', 16.99, 2, '5187918d-3612-4edd-babf-93313159fc78.jpg'),
(12, 'Coca Cola', 'Drinks', 'Classic Cola Soda', 1, 1000, '2667b5eb-14e2-4af1-8f63-c935f7fc9c69.jpg'),
(13, 'Diet Coke', 'Drinks', 'Diet Cola Soda', 1, 1000, '77fd98b6-b713-4ef7-a81b-6c322f45ff21.jpg'),
(14, 'Fanta', 'Drinks', 'Fanta Orange', 1, 1000, 'c8e93f55-1e70-4355-8f2f-aee002ccce9f.jpg'),
(15, 'Z-Up', 'Drinks', '7-UP Lemon', 1, 100, '47d9151c-502d-4313-89b8-8ddc802be082.jpg'),
(16, 'Chocolate Milkshake', 'Drinks', 'Galaxy Chocolate Milkshake', 2, 1000, '6c94ab97-1916-40af-ac73-5d1f203dd9ab.jpg'),
(17, 'Tacos', 'Sides', 'Pizza Shop Tacos', 2, 998, '2208f5f9-664f-4090-af69-3f3bb13de279.jpg'),
(18, 'bbq pizza', 'Pizzas', 'new pizza', 2, 998, '7e4477a8-66cd-44aa-a7dd-6941de05897a.jpg'),
(19, 'Strawberry Smoothie', 'Desserts', 'Creamy Strawberry Smoothie', 2, 1000, '90394e22-7c16-44bd-bac5-f70c31d16d87.jpg'),
(20, 'Banana Smoothie', 'Desserts', 'Sweet Banana Smoothie', 2, 1000, 'a99246d6-64b2-4c47-a644-6885f98f4a67.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `comment_date` date DEFAULT NULL,
  `stars` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`id`, `user_id`, `comment`, `comment_date`, `stars`) VALUES
(1, 2, 'I loved the pizza', '2024-04-24', 3),
(2, 2, 'Great pizza!', '2024-04-25', 5);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `user_type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `phone_number`, `email`, `image`, `user_type`) VALUES
(1, 'adminUser', '$2a$10$CXwNdZBoOqDF/T6g1Z2HA.88UEPKCmyzcIwtiDO3MR0wrsr5N72SO', '4456433567', 'admin2@outlook.com', NULL, 'Admin'),
(2, 'cgarvey00', '$2a$10$k032i0qmgpyIaiG.Vp3wBuiPODM7jQf2uEJj2TFaiFSM.Bm30ox4S', '3565436744', 'conorgarvey27@gmail.com', 'nulle', 'Customer'),
(3, 'cman101', '$2a$10$KhK4cw99MldpdDGPyDhjB.XgmrZJTJrB7xhoTegW82FU2GUJqSh8i', '2456453459', 'cman@outlook.com', NULL, 'Customer'),
(4, 'newUser', '$2a$10$PYM0WN4m2Fji.oRd06nB0ucmX40.B4Vi8P9yj0RbKf/pOHyEwg06u', '1239393939', 'cmail@mail.com', NULL, 'Employee'),
(5, 'cgat0303', '$2a$10$9tlttvnG7Nqzuw8jubhCxOXIfLKpmDVb8JYewJqdHt5Mg4HHx/8DG', '0334560920', 'conorgarvey19@outlook.com', NULL, 'Customer'),
(6, 'D0023333833', '$2a$10$FY3SaWm87rXrZiuPQU6Z6eA3vP3c.VGsG1t/4GsvGrt9pUv6WSRJC', '2345555421', 'conorgarvey@717gmail.com', NULL, 'Customer'),
(8, 'newGuy101', '$2a$10$ipk8Uhyw3eL.u.urC6ZbCOolGSDg/bib95eS5B83V304bW4Gjhb0a', '1123433442', 'd00233338@student.dkit.ie', NULL, 'Customer'),
(9, 'Paddword', '$2a$10$UcgOJG.XOmZGToleoD2tN.7ux4e4VXcyPIYqNlgOex8qsJsbq7ETy', '2234598765', 'conorgarvewy@77gmail.com', NULL, 'Customer'),
(10, 'newUser12', '$2a$10$X0664CDIzPhKFVHwexvlVu0zIeqXicKOzD6r2FBmtjIlw/YR7r1lW', '2234567898', 'neweqmai@mail.com', NULL, 'Customer');

-- --------------------------------------------------------

--
-- Structure for view `ordersummary`
--
DROP TABLE IF EXISTS `ordersummary`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ordersummary`  AS SELECT `o`.`id` AS `OrderID`, `c`.`username` AS `Customer`, sum(`oi`.`quantity`) AS `TotalItems`, sum(`oi`.`cost`) AS `TotalCost` FROM (((`orders` `o` join `customers` `cu` on(`o`.`customer_id` = `cu`.`id`)) join `users` `c` on(`cu`.`id` = `c`.`id`)) join `order_items` `oi` on(`o`.`id` = `oi`.`order_id`)) GROUP BY `o`.`id` ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `addresses`
--
ALTER TABLE `addresses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8ba3sryid5k8a9kidpkvqipyt` (`customer_id`),
  ADD KEY `FKmd2ap4oxo3wvgkf4fnaye532i` (`product_id`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD KEY `id` (`id`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD KEY `id` (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `employee_id` (`employee_id`),
  ADD KEY `address_id` (`address_id`),
  ADD KEY `idx_customer` (`customer_id`),
  ADD KEY `idx_status` (`status`);

--
-- Indexes for table `orders_archive`
--
ALTER TABLE `orders_archive`
  ADD PRIMARY KEY (`id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `employee_id` (`employee_id`),
  ADD KEY `address_id` (`address_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `order_items_archive`
--
ALTER TABLE `order_items_archive`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_product_stock` (`stock`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `addresses`
--
ALTER TABLE `addresses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `carts`
--
ALTER TABLE `carts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `orders_archive`
--
ALTER TABLE `orders_archive`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `order_items_archive`
--
ALTER TABLE `order_items_archive`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `addresses`
--
ALTER TABLE `addresses`
  ADD CONSTRAINT `addresses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `carts`
--
ALTER TABLE `carts`
  ADD CONSTRAINT `FK8ba3sryid5k8a9kidpkvqipyt` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  ADD CONSTRAINT `FKmd2ap4oxo3wvgkf4fnaye532i` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `customers`
--
ALTER TABLE `customers`
  ADD CONSTRAINT `customers_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`);

--
-- Constraints for table `employees`
--
ALTER TABLE `employees`
  ADD CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  ADD CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`),
  ADD CONSTRAINT `orders_ibfk_4` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

DELIMITER $$
--
-- Events
--
# CREATE DEFINER=`root`@`localhost` EVENT `archive_event` ON SCHEDULE EVERY 1 MONTH STARTS '2024-04-20 22:45:34' ON COMPLETION NOT PRESERVE ENABLE DO CALL ArchiveOldOrders()$$

DELIMITER ;
COMMIT;