package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.AccountGetAllResponseDTO;
import org.example.dto.AccountGetByIdResponseDTO;
import org.example.repository.AccountRepository;
import org.example.security.Authentication;
import org.example.security.ForbiddenException;
import org.example.security.NotFoundException;
import org.example.security.Roles;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;


    public List<AccountGetAllResponseDTO> getAll(final Authentication auth) {
        if (auth.isAnonymous()){
            throw new ForbiddenException();
        }
        if (auth.hasRole(Roles.ACCOUNTS_VIEW_ALL)){
            return repository.getAll().stream()
                    .map(o -> new AccountGetAllResponseDTO(
                            o.getId(),
                            o.getBalance()
                    ))
                    .collect(Collectors.toList());
        }

        return repository.getAllByOwner(auth.getname()).stream()
                .map(o -> new AccountGetAllResponseDTO(
                        o.getId(),
                        o.getBalance()
                ))
                .collect(Collectors.toList());
    }

    public AccountGetByIdResponseDTO getById(Authentication auth, String id) {
        if (auth.isAnonymous()){
            throw new ForbiddenException();
        }
        return repository.getById(id)
                .filter(o -> o.getOwner().equals(auth.getname()))
                .map(o -> new AccountGetByIdResponseDTO(
                        o.getId(),
                        o.getBalance()
                ))
                .orElseThrow(NotFoundException::new)
                ;
    }
}
