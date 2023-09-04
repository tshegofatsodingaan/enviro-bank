CREATE TABLE IF NOT EXISTS customer (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    id_number BIGINT NOT NULL,
    phone_number BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS account (
    id SERIAL PRIMARY KEY NOT NULL,
    account_num SERIAL NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    customer_num VARCHAR(50) NOT NULL,
    account_balance VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_num SERIAL NOT NULL,
    type_of_transaction VARCHAR(50) NOT NULL,
    transaction_amount DECIMAL NOT NULL,
    active BOOLEAN DEFAULT false,
    account_id INTEGER REFERENCES account(id)
);

