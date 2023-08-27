
CREATE TABLE IF NOT EXISTS accounts (
    account_num SERIAL PRIMARY KEY,
    account_type VARCHAR(50) NOT NULL,
    customer_num VARCHAR(50) NOT NULL,
    account_balance VARCHAR(50) NOT NULL,
    overdraft VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customer_num VARCHAR(25) NOT NULL,
    account_num SERIAL NOT NULL,
    type_of_transaction VARCHAR(50) NOT NULL,
    account_type VARCHAR(25) NOT NULL,
    withdrawal_amount VARCHAR(50) NOT NULL,
    CONSTRAINT foreign_key_account_num FOREIGN KEY(account_num) REFERENCES accounts(account_num) ON UPDATE CASCADE ON DELETE CASCADE
);