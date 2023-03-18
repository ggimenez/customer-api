
CREATE TABLE `customers-db`.customer (
                                         id INT auto_increment NOT NULL,
                                         name varchar(100) NOT NULL,
                                         last_name varchar(100) NOT NULL,
                                         address varchar(500) NOT NULL,
                                         email varchar(100) NOT NULL,
                                         phone_number varchar(100) NOT NULL,
                                         CONSTRAINT customer_PK PRIMARY KEY (id)
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;