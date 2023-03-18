--liquibase formatted sql

--changeset gabri:1

INSERT INTO `customers-db`.customer
(id, name, last_name, address, email, phone_number)
VALUES(1, 'Gabriel', 'Giménez', 'una dirección', 'mail@mail.com', '0981222333');
INSERT INTO `customers-db`.customer
( id, name, last_name, address, email, phone_number)
VALUES( 2, 'María', 'Giménez', 'la dirección de María', 'maria@mail.com', '0981333444');

