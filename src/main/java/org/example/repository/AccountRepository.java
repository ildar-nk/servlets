package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.entity.AccountEntity;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

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

}
