-- Nexa Business Suite - PostgreSQL setup template.
-- Replace __DB_PASSWORD__ before running this template manually.
CREATE USER nexa WITH PASSWORD '__DB_PASSWORD__';
CREATE DATABASE nexa_business OWNER nexa;
