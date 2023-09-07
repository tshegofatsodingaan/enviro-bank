CREATE TABLE IF NOT EXISTS account (
    id SERIAL PRIMARY KEY NOT NULL,
    account_num INTEGER NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    customer_num VARCHAR(50) NOT NULL,
    account_balance DECIMAL NOT NULL,
    active BOOLEAN DEFAULT true
);

CREATE TABLE IF NOT EXISTS transaction (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_num SERIAL NOT NULL,
    type_of_transaction VARCHAR(50) NOT NULL,
    transaction_amount DECIMAL NOT NULL,
    account_id INTEGER REFERENCES account(id)
);

