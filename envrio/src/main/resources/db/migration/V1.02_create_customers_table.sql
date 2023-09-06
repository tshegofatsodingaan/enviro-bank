CREATE TABLE IF NOT EXISTS customer (
   id SERIAL PRIMARY KEY NOT NULL,
   name VARCHAR(50) NOT NULL,
   surname VARCHAR(50) NOT NULL,
   id_number BIGINT NOT NULL,
   phone_number BIGINT NOT NULL
);

ALTER TABLE account
ADD customer_id INTEGER REFERENCES customer(id);