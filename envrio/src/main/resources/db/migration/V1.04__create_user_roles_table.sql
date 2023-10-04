CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50),
    fk_user_id INTEGER,
    FOREIGN KEY (fk_user_id) REFERENCES users(id)
);

ALTER TABLE users ALTER COLUMN password DROP NOT NULL;

ALTER TABLE users ADD UNIQUE (id_number);
ALTER TABLE users ADD UNIQUE (phone_number);

ALTER TABLE roles DROP COLUMN fk_user_id;

ALTER TABLE users ADD COLUMN user_role_id INTEGER ;


ALTER TABLE users
    ADD FOREIGN KEY (user_role_id) REFERENCES roles(id);

ALTER TABLE users DROP COLUMN user_role_id;

CREATE TABLE IF NOT EXISTS users_roles (
    user_id INTEGER REFERENCES users(id),
    role_id INTEGER REFERENCES roles(id)
);

ALTER TABLE transaction ADD COLUMN date_of_transaction DATE DEFAULT CURRENT_DATE;
ALTER TABLE transaction ADD COLUMN receiver_account_num INTEGER;