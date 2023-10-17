ALTER TABLE account ADD COLUMN available_balance DECIMAL;
ALTER TABLE users ADD COLUMN accounts INTEGER;

ALTER TABLE users DROP COLUMN accounts;