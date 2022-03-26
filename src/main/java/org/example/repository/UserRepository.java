package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.entity.UserEntity;
import org.jdbi.v3.core.Jdbi;

import java.util.Arrays;
import java.util.List;

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
}
