-- INSERT INTO users(login) VALUES ('admin')
INSERT INTO users (login, password, roles)
VALUES ('admin', '$argon2id$v=19$m=4096,t=3,p=1$oXKudXfX2sTIGm2ZMb4b6w$nOLtvVFbOk9U7aBhJoxOKGOS4Uve60JT5/NM2b2AUr4', '{ACCOUNTS_VIEW_ALL, ACCOUNTS_EDIT_ALL, USERS_VIEW_ALL, USERS_EDIT_ALL}'),
       ('operator', '$argon2id$v=19$m=4096,t=3,p=1$oXKudXfX2sTIGm2ZMb4b6w$nOLtvVFbOk9U7aBhJoxOKGOS4Uve60JT5/NM2b2AUr4', '{ACCOUNTS_VIEW_ALL, USERS_VIEW_ALL}'),
       ('vasya', '$argon2id$v=19$m=4096,t=3,p=1$oXKudXfX2sTIGm2ZMb4b6w$nOLtvVFbOk9U7aBhJoxOKGOS4Uve60JT5/NM2b2AUr4', DEFAULT),
       ('petya', '$argon2id$v=19$m=4096,t=3,p=1$oXKudXfX2sTIGm2ZMb4b6w$nOLtvVFbOk9U7aBhJoxOKGOS4Uve60JT5/NM2b2AUr4', DEFAULT);

INSERT INTO accounts(id, owner, balance)
VALUES (gen_random_uuid()::text, 'vasya', 10000),
       (gen_random_uuid()::text, 'petya', 20000);
