package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserGetAllResponseDTO;
import org.example.dto.UserRegisterRequestDTO;
import org.example.dto.UserRegisterResponsetDTO;
import org.example.entity.UserEntity;
import org.example.exception.UsernameAlreadyRegisteredException;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    public List<UserGetAllResponseDTO> getAll() {
        return repository.getAll()
                .stream()
                .map(o -> new UserGetAllResponseDTO(o.getId(), o.getLogin()))
                .collect(Collectors.toList());
    }

    public UserRegisterResponsetDTO register(UserRegisterRequestDTO requestData) {
        final String hashedPassword = passwordEncoder.encode(requestData.getPassword());
        repository.findByLogin(requestData.getLogin()).ifPresent(o -> {
            throw new UsernameAlreadyRegisteredException(o.getLogin());
        });
        final UserEntity saved = repository.save(new UserEntity(
                0L,
                requestData.getLogin(),
                hashedPassword
        ));
        return new UserRegisterResponsetDTO(saved.getId(), saved.getLogin());
    }
}
