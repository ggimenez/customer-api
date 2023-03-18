--liquibase formatted sql

--changeset gabri:1

INSERT INTO  `customers-db`.role
(id, name)
VALUES(1, 'ADMIN');


