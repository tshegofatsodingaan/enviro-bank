CREATE TABLE IF NOT EXISTS customer (
   id SERIAL PRIMARY KEY NOT NULL,
   name VARCHAR(50) NOT NULL,
   surname VARCHAR(50) NOT NULL,
   id_number VARCHAR(13) NOT NULL,
   phone_number VARCHAR(10) NOT NULL
);

ALTER TABLE account
ADD customer_id INTEGER,
    ADD CONSTRAINT customer FOREIGN KEY (customer_id) REFERENCES customer(id);

