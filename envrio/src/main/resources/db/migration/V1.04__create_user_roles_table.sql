CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50),
    fk_user_id INTEGER,
    FOREIGN KEY (fk_user_id) REFERENCES users(id)
);

ALTER TABLE users ALTER COLUMN password DROP NOT NULL;

ALTER TABLE users ADD UNIQUE (id_number);
ALTER TABLE users ADD UNIQUE (phone_number);
