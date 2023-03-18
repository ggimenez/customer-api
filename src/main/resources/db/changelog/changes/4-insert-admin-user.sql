--liquibase formatted sql

--changeset gabri:1

INSERT INTO  `customers-db`.user
(id, user_name, email, password)
VALUES(1, 'admin', 'admin@customersapi.com', '$2a$10$8YbJz0ZpqlS8Ty0GCgxOi.HCmfyC4713tLmbT6QJGoXSoJ2KVogsi');

