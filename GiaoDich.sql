-- DROP DATABASE IF EXISTS transaction;

CREATE DATABASE transaction;

USE transaction;

CREATE TABLE transaction (
    id VARCHAR(10) PRIMARY KEY,
    date DATE,
    unitPrice DOUBLE,
    area DOUBLE,
    transactionType VARCHAR(10),
    landType VARCHAR(10),
    houseType VARCHAR(20),
    address VARCHAR(100)
);

INSERT INTO
    transaction (
        id,
        date,
        unitPrice,
        area,
        transactionType,
        landType,
        houseType,
        address
    )
VALUES (
        'GD1',
        '2024-10-20',
        1000000,
        80,
        'GDD',
        'B',
        NULL,
        '05 Quang Trung, Go Vap'
    ),
    (
        'GD2',
        '2023-04-20',
        800000,
        90,
        'GDN',
        NULL,
        'normal',
        '56 Nguyen Ba Tuyen, Go Vap'
    );

select * from transaction;