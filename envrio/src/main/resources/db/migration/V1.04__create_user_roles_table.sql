CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50),
    fk_user_id INTEGER,
    FOREIGN KEY (fk_user_id) REFERENCES users(id)
);