package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserGetAllResponseDTO;
import org.example.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<UserGetAllResponseDTO> getAll() {
        return repository.getAll()
                .stream()
                .map(o -> new UserGetAllResponseDTO(o.getId(), o.getLogin()))
                .collect(Collectors.toList());
    }
}
