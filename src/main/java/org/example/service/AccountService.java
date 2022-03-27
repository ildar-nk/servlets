package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.AccountGetAllResponseDTO;
import org.example.repository.AccountRepository;
import org.example.security.Authentication;
import org.example.security.ForbiddenException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;


    public List<AccountGetAllResponseDTO> getAll(final Authentication auth) {
        if (auth.isAnonymous()){
            throw new ForbiddenException();
        }
        return repository.getAllByOwner(auth.getname()).stream()
                .map(o -> new AccountGetAllResponseDTO(
                        o.getId(),
                        o.getBalance()
                ))
                .collect(Collectors.toList());
    }
}
