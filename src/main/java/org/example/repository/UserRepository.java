package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.entity.UserEntity;
import org.jdbi.v3.core.Jdbi;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserRepository {
    private final Jdbi jdbi;

    public List<UserEntity> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery(
                // language=PostgreSQL
                "SELECT id, login FROM users ORDER BY id")
                .mapToBean(UserEntity.class)
                .list());
    }

    public Optional<UserEntity> findByLogin(String login) {
        return jdbi.withHandle(handle -> handle.createQuery(
                // language=PostgreSQL
                "SELECT id, login FROM users WHERE login = :login"
                ).bind("login", login).mapToBean(UserEntity.class).findOne());

    }

    public UserEntity save(UserEntity entity){
        return jdbi.withHandle(handle -> handle.createQuery(
                // language=PostgreSQL
                """
                INSERT INTO users(login, password) VALUES (:login, :password)
                RETURNING id, login, password
                """
                ).bind("login", entity.getLogin())
                .bind("password", entity.getPassword())
                .mapToBean(UserEntity.class)
                .one());
    }
}
