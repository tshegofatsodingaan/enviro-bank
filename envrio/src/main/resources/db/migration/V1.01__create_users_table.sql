
CREATE TABLE IF NOT EXISTS accounts (
    id SERIAL PRIMARY KEY NOT NULL,
    account_num SERIAL NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    customer_num VARCHAR(50) NOT NULL,
    account_balance VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_num SERIAL NOT NULL,
    type_of_transaction VARCHAR(50) NOT NULL,
    transaction_amount VARCHAR(50) NOT NULL,
    active BOOLEAN DEFAULT false,
    account_id INTEGER REFERENCES accounts(id)
);

