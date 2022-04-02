CREATE TABLE users
(
    id      BIGSERIAL PRIMARY KEY,
    login    TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

CREATE TABLE accounts (
    id TEXT PRIMARY KEY,
    owner TEXT NOT NULL,
    balance INT NOT NULL DEFAULT 0

);

CREATE TABLE tokens (
  value TEXT PRIMARY KEY,
  user_id BIGSERIAL NOT NULL REFERENCES users
);