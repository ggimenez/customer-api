--liquibase formatted sql

--changeset gabri:1

-- SongsDB.`role` definition

CREATE TABLE  `customers-db`.role (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(200) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB
  AUTO_INCREMENT=2
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;

-- rollback drop table role