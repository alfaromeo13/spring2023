-- create the databases
CREATE DATABASE IF NOT EXISTS new_database;

-- create the users for each database
CREATE USER 'jovan123'@'%' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON new_database.* TO 'jovan123'@'%';

FLUSH PRIVILEGES;

USE new_database;

CREATE TABLE `categories` (
                              `id` int(11) NOT NULL,
                              `name` varchar(50) NOT NULL,
                              `description` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `categories` (`id`, `name`, `description`) VALUES
                                                           (1, 'updated name 111', 'desc'),
                                                           (4, 'category2', 'description :D'),
                                                           (5, 'category3', 'desc'),
                                                           (6, '1234', 'asdasdasd'),
                                                           (7, '1234', 'asdasdasd'),
                                                           (8, 'catst', 'asdasdasd');


CREATE TABLE `customers` (
                             `id` bigint(20) NOT NULL,
                             `full_name` varchar(50) NOT NULL,
                             `type` enum('INDIVIDUAL','LEGAL') NOT NULL,
                             `is_vip` bit(1) NOT NULL DEFAULT b'0',
                             `joined_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `customers` (`id`, `full_name`, `type`, `is_vip`, `joined_at`) VALUES
                                                                               (2, 'vlado doderovic', 'LEGAL', b'1', '2023-10-01 20:24:39'),
                                                                               (3, 'Ivica Vukovic', 'INDIVIDUAL', b'0', '2023-10-01 21:25:14'),
                                                                               (5, 'Ivica Vukovic', 'INDIVIDUAL', b'0', '2023-10-01 21:33:16'),
                                                                               (6, 'Jovan Vukovic', 'INDIVIDUAL', b'0', '2023-10-01 21:34:09'),
                                                                               (7, 'Jovan Vukovic', 'INDIVIDUAL', b'0', '2023-10-20 21:12:52'),
                                                                               (8, 'Jovan Vukovic', 'INDIVIDUAL', b'0', '2023-10-20 21:14:08'),
                                                                               (9, 'Jovan Vukovic', 'INDIVIDUAL', b'0', '2024-01-08 22:28:48');


CREATE TABLE `DATABASECHANGELOG` (
                                     `ID` varchar(255) NOT NULL,
                                     `AUTHOR` varchar(255) NOT NULL,
                                     `FILENAME` varchar(255) NOT NULL,
                                     `DATEEXECUTED` datetime NOT NULL,
                                     `ORDEREXECUTED` int(11) NOT NULL,
                                     `EXECTYPE` varchar(10) NOT NULL,
                                     `MD5SUM` varchar(35) DEFAULT NULL,
                                     `DESCRIPTION` varchar(255) DEFAULT NULL,
                                     `COMMENTS` varchar(255) DEFAULT NULL,
                                     `TAG` varchar(255) DEFAULT NULL,
                                     `LIQUIBASE` varchar(20) DEFAULT NULL,
                                     `CONTEXTS` varchar(255) DEFAULT NULL,
                                     `LABELS` varchar(255) DEFAULT NULL,
                                     `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `DATABASECHANGELOG` (`ID`, `AUTHOR`, `FILENAME`, `DATEEXECUTED`, `ORDEREXECUTED`, `EXECTYPE`, `MD5SUM`, `DESCRIPTION`, `COMMENTS`, `TAG`, `LIQUIBASE`, `CONTEXTS`, `LABELS`, `DEPLOYMENT_ID`) VALUES
    ('202310191520', 'j0dza_', 'liquibase/changelogs/202310191520_add_column_created_at_to_comuent.xml', '2023-10-19 15:33:36', 1, 'EXECUTED', '9:4b44784e3ac5dfc92b7c2adc8a0e975d', 'addColumn tableName=documents', '', NULL, '4.24.0', NULL, NULL, '7722416868');

CREATE TABLE `DATABASECHANGELOGLOCK` (
                                         `ID` int(11) NOT NULL,
                                         `LOCKED` tinyint(1) NOT NULL,
                                         `LOCKGRANTED` datetime DEFAULT NULL,
                                         `LOCKEDBY` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `DATABASECHANGELOGLOCK` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
    (1, 0, NULL, NULL);


CREATE TABLE `departments` (
                               `id` int(11) NOT NULL,
                               `name` varchar(100) NOT NULL,
                               `description` varchar(255) DEFAULT NULL,
                               `is_active` bit(1) NOT NULL DEFAULT b'1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `departments` (`id`, `name`, `description`, `is_active`) VALUES
    (1, 'ProgrammingDPU', NULL, b'1');


CREATE TABLE `documents` (
                             `id` int(11) NOT NULL,
                             `name` varchar(255) NOT NULL,
                             `path` varchar(255) NOT NULL,
                             `created_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `orders` (
                          `id` int(11) NOT NULL,
                          `fk_customer` bigint(20) NOT NULL,
                          `created_at` datetime NOT NULL,
                          `total_price` decimal(15,2) NOT NULL,
                          `status` enum('CREATED','IN_PROGRESS','REJECTED','DONE') DEFAULT 'CREATED'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `orders` (`id`, `fk_customer`, `created_at`, `total_price`, `status`) VALUES
                                                                                      (2, 3, '2023-10-01 21:26:23', '22.50', 'DONE'),
                                                                                      (4, 3, '2023-10-20 21:12:52', '22.50', 'IN_PROGRESS'),
                                                                                      (5, 3, '2023-10-20 21:14:09', '22.50', 'IN_PROGRESS'),
                                                                                      (6, 3, '2024-01-08 22:28:49', '22.50', 'IN_PROGRESS');


CREATE TABLE `orders_items` (
                                `id` int(11) NOT NULL,
                                `fk_product` int(11) NOT NULL,
                                `quantity` int(11) NOT NULL,
                                `fk_order` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `orders_items` (`id`, `fk_product`, `quantity`, `fk_order`) VALUES
                                                                            (5, 11, 10, 2),
                                                                            (6, 16, 3, 2),
                                                                            (7, 15, 4, 2),
                                                                            (11, 11, 10, 4),
                                                                            (12, 16, 3, 4),
                                                                            (13, 15, 4, 4),
                                                                            (14, 11, 10, 5),
                                                                            (15, 16, 3, 5),
                                                                            (16, 15, 4, 5),
                                                                            (17, 11, 10, 6),
                                                                            (18, 16, 3, 6),
                                                                            (19, 15, 4, 6);


CREATE TABLE `products` (
                            `id` int(11) NOT NULL,
                            `title` varchar(50) NOT NULL,
                            `short_description` varchar(100) NOT NULL,
                            `long_description` text DEFAULT NULL,
                            `price` double NOT NULL,
                            `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                            `is_active` bit(1) NOT NULL,
                            `fk_category` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `products` (`id`, `title`, `short_description`, `long_description`, `price`, `created_at`, `is_active`, `fk_category`) VALUES
                                                                                                                                       (11, 'coca cola', 'shortDesc', NULL, 22.3, '2023-09-30 22:37:48', b'0', 4),
                                                                                                                                       (15, 'pepsi', 'newShort', 'newLong', 22.5, '2023-10-01 12:36:04', b'0', 4),
                                                                                                                                       (16, 'zero seven', 'newShort', 'newLong', 22.5, '2023-10-01 12:37:04', b'0', 4),
                                                                                                                                       (17, 'newTitle', 'newShort', 'newLong', 22.5, '2023-10-01 12:37:35', b'0', 4),
                                                                                                                                       (18, 'newTitle', 'newShort', 'newLong', 22.5, '2023-10-06 00:38:28', b'0', 4),
                                                                                                                                       (19, 'evo da probamo', 'newShort', 'newLong', 22.5, '2024-01-08 23:41:41', b'0', 4);

CREATE TABLE `roles` (
                         `id` int(11) NOT NULL,
                         `name` varchar(100) NOT NULL,
                         `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `roles` (`id`, `name`, `description`) VALUES
    (6, 'admin', NULL);

CREATE TABLE `users` (
                         `id` bigint(20) NOT NULL,
                         `first_name` varchar(20) NOT NULL,
                         `last_name` varchar(20) NOT NULL,
                         `username` varchar(20) NOT NULL,
                         `password` varchar(100) NOT NULL,
                         `age` int(11) NOT NULL,
                         `fk_department` int(11) NOT NULL,
                         `is_active` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `users` (`id`, `first_name`, `last_name`, `username`, `password`, `age`, `fk_department`, `is_active`) VALUES
                                                                                                                       (15, 'vlado', 'doderovic', 'john', '$2y$10$VLQ4dkfWtDq.ydWADOcKQ.i6YLKFlPeS9CAqmx9QoeRWjfFTyha7u', 22, 1, b'1'),
                                                                                                                       (16, 'A', 'B', 'abc', '123', 22, 1, b'0'),
                                                                                                                       (17, 'A', 'B', 'abc', '123', 22, 1, b'0'),
                                                                                                                       (18, 'A', 'B', 'abc', '123', 22, 1, b'0');

CREATE TABLE `users_roles` (
                               `fk_user` bigint(20) NOT NULL,
                               `fk_role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `users_roles` (`fk_user`, `fk_role`) VALUES
                                                     (15, 6),
                                                     (16, 6),
                                                     (17, 6),
                                                     (18, 6);

ALTER TABLE `categories`
    ADD PRIMARY KEY (`id`);


ALTER TABLE `customers`
    ADD PRIMARY KEY (`id`);


ALTER TABLE `DATABASECHANGELOGLOCK`
    ADD PRIMARY KEY (`ID`);


ALTER TABLE `departments`
    ADD PRIMARY KEY (`id`);

ALTER TABLE `documents`
    ADD PRIMARY KEY (`id`);


ALTER TABLE `orders`
    ADD PRIMARY KEY (`id`),
  ADD KEY `fk_customer` (`fk_customer`);


ALTER TABLE `orders_items`
    ADD PRIMARY KEY (`id`),
  ADD KEY `fk_order` (`fk_order`),
  ADD KEY `fk_product` (`fk_product`);


ALTER TABLE `products`
    ADD PRIMARY KEY (`id`),
  ADD KEY `fk_category` (`fk_category`);


ALTER TABLE `roles`
    ADD PRIMARY KEY (`id`);


ALTER TABLE `users`
    ADD PRIMARY KEY (`id`),
  ADD KEY `fk_department` (`fk_department`);

ALTER TABLE `users_roles`
    ADD PRIMARY KEY (`fk_user`,`fk_role`),
  ADD KEY `fk_role` (`fk_role`);


ALTER TABLE `categories`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;


ALTER TABLE `customers`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;


ALTER TABLE `departments`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;


ALTER TABLE `documents`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

ALTER TABLE `orders`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

ALTER TABLE `orders_items`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;


ALTER TABLE `products`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

ALTER TABLE `roles`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;


ALTER TABLE `users`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;


ALTER TABLE `orders`
    ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`fk_customer`) REFERENCES `customers` (`id`);


ALTER TABLE `orders_items`
    ADD CONSTRAINT `orders_items_ibfk_1` FOREIGN KEY (`fk_order`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `orders_items_ibfk_2` FOREIGN KEY (`fk_product`) REFERENCES `products` (`id`);


ALTER TABLE `products`
    ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`fk_category`) REFERENCES `categories` (`id`);


ALTER TABLE `users`
    ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`fk_department`) REFERENCES `departments` (`id`);


ALTER TABLE `users_roles`
    ADD CONSTRAINT `users_roles_ibfk_1` FOREIGN KEY (`fk_role`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `users_roles_ibfk_2` FOREIGN KEY (`fk_user`) REFERENCES `users` (`id`);
COMMIT;
