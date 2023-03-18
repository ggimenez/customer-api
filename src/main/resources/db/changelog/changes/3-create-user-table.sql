--liquibase formatted sql

--changeset gabri:1

-- SongsDB.`user` definition

CREATE TABLE `customers-db`.user (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `user_name` varchar(100) NOT NULL,
                        `email` varchar(200) NOT NULL,
                        `password` varchar(200) NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `user_UN_user_name` (`user_name`),
                        UNIQUE KEY `user_UN_email` (`email`)
)   ENGINE=InnoDB
    AUTO_INCREMENT=2
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

-- rollback drop table user