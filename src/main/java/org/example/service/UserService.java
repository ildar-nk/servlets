package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserGetAllResponseDTO;
import org.example.dto.UserRegisterRequestDTO;
import org.example.dto.UserRegisterResponsetDTO;
import org.example.entity.TokenEntity;
import org.example.entity.UserEntity;
import org.example.exception.UsernameAlreadyRegisteredException;
import org.example.repository.UserRepository;
import org.example.security.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom random = new SecureRandom();

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

        byte[] buffer = new byte[128];
        random.nextBytes(buffer);
        final String token = Base64.getUrlEncoder().withoutPadding().encodeToString(buffer);
        repository.save(new TokenEntity(saved.getId(), token));

        return new UserRegisterResponsetDTO(saved.getId(), saved.getLogin(), token);
    }

    public LoginAuthentification authentificate(String login, String password) {
        final UserEntity entity = repository.findByLogin(login).orElseThrow(NotFoundException::new);

        if (!passwordEncoder.matches(password, entity.getPassword())){
            throw new CredentialNotMatchesException();
        }

        return new LoginAuthentification(login);
    }

    public TokenAuthentification authentificate(String token) {
        return repository.findByTokin(token)
                .map(o -> new TokenAuthentification(o.getLogin()))
                .orElseThrow(TokenNotFoundException::new)
                ;
    }
}
