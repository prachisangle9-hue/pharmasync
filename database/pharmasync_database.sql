CREATE DATABASE IF NOT EXISTS pharmasync;

USE pharmasync;

CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('Admin','Pharmacist') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS medicines (
    medicine_id INT AUTO_INCREMENT PRIMARY KEY,
    medicine_name VARCHAR(100) NOT NULL,
    company VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    batch_no VARCHAR(50) UNIQUE,
    manufacture_date DATE,
    expiry_date DATE,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    barcode VARCHAR(100) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

SHOW TABLES;