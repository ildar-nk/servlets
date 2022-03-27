-- INSERT INTO users(login) VALUES ('admin')

INSERT INTO accounts(id, owner, balance)
VALUES (gen_random_uuid()::text, 'vasya', 10000),
       (gen_random_uuid()::text, 'petya', 20000);
