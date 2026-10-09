-- Nexa Business Suite - MariaDB setup template.
-- __DB_PASSWORD__ is replaced at runtime by setup-mariadb.bat.
CREATE DATABASE IF NOT EXISTS nexa_business CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'nexa'@'localhost' IDENTIFIED BY '__DB_PASSWORD__';
ALTER USER 'nexa'@'localhost' IDENTIFIED BY '__DB_PASSWORD__';
GRANT ALL PRIVILEGES ON nexa_business.* TO 'nexa'@'localhost';
CREATE USER IF NOT EXISTS 'nexa'@'127.0.0.1' IDENTIFIED BY '__DB_PASSWORD__';
ALTER USER 'nexa'@'127.0.0.1' IDENTIFIED BY '__DB_PASSWORD__';
GRANT ALL PRIVILEGES ON nexa_business.* TO 'nexa'@'127.0.0.1';
FLUSH PRIVILEGES;
