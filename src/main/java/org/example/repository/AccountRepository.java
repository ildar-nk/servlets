package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.dto.AccountGetByIdResponseDTO;
import org.example.entity.AccountEntity;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AccountRepository {
    private final Jdbi jdbi;

    public List<AccountEntity> getAllByOwner(final String owner){
        return jdbi.withHandle(handle -> handle.createQuery(
                // language=PostgreSQL
                "SELECT id, owner, balance FROM accounts WHERE owner = :owner")
                .bind("owner", owner)
                .mapToBean(AccountEntity.class)
                .list()
        );
    }
    public List<AccountEntity> getAll(){
        return jdbi.withHandle(handle -> handle.createQuery(
                // language=PostgreSQL
                "SELECT id, owner, balance FROM accounts") // order by limit offset
                .mapToBean(AccountEntity.class)
                .list()
        );
    }

    public Optional<AccountEntity> getById(final String id) {
        return jdbi.withHandle(handle -> handle.createQuery(
                // language=PostgreSQL
                "SELECT id, owner, balance FROM accounts WHERE id = :id"
                )
                .bind("id", id)
                .mapToBean(AccountEntity.class)
                .findOne()
        );
    }
}
