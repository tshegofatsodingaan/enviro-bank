CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE TABLE IF NOT EXISTS new_user (
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    customerNum VARCHAR(25) NOT NULL,
    account_num UUID NOT NULL,
    type_of_transaction VARCHAR(50) NOT NULL,
    account_type VARCHAR(25) NOT NULL,
    withdrawal_amount VARCHAR(50) NOT NULL
);