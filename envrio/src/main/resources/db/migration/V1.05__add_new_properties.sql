ALTER TABLE account ADD COLUMN available_balance DECIMAL;
ALTER TABLE users ADD COLUMN accounts INTEGER;

ALTER TABLE users DROP COLUMN accounts;

ALTER TABLE transaction ADD COLUMN sender_account_num INTEGER;
ALTER TABLE transaction DROP COLUMN account_num;

ALTER TABLE transaction ADD COLUMN pending BOOLEAN;