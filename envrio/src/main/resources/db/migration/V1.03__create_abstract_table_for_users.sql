CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    id_number VARCHAR(13) NOT NULL,
    phone_number VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS admin (
    id SERIAL PRIMARY KEY NOT NULL REFERENCES "user"(id)
);

ALTER TABLE customer DROP COLUMN name;
ALTER TABLE customer DROP COLUMN surname;
ALTER TABLE customer DROP COLUMN id_number;
ALTER TABLE customer DROP COLUMN phone_number;

ALTER TABLE customer
    ADD CONSTRAINT customer FOREIGN KEY (id) REFERENCES "user"(id);