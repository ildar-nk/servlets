-- CREATE TABLE users
-- (
--     id      BIGSERIAL PRIMARY KEY,
--     login    TEXT NOT NULL UNIQUE
-- );

CREATE TABLE accounts (
    id TEXT PRIMARY KEY,
    owner TEXT NOT NULL,
    balance INT NOT NULL DEFAULT 0

);