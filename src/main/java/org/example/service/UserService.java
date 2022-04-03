package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
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

    public List<UserGetAllResponseDTO> getAll(Authentication auth) {
        if (!auth.hasRole(Roles.USERS_VIEW_ALL)){
            throw new ForbiddenException();
        }
        return repository.getAll()
                .stream()
                .map(o -> new UserGetAllResponseDTO(o.getId(), o.getLogin()))
                .collect(Collectors.toList());
    }

    public UserRegisterResponseDTO register(UserRegisterRequestDTO requestData) {
        final String hashedPassword = passwordEncoder.encode(requestData.getPassword());
        repository.findByLogin(requestData.getLogin()).ifPresent(o -> {
            throw new UsernameAlreadyRegisteredException(o.getLogin());
        });
        final UserEntity saved = repository.save(new UserEntity(
                0L,
                requestData.getLogin(),
                hashedPassword,
                new String[]{}
        ));

        final String token = createToken(saved);

        return new UserRegisterResponseDTO(saved.getId(), saved.getLogin(), token);
    }

    public LoginAuthentification authentificate(String login, String password) {
        final UserEntity entity = repository.findByLogin(login).orElseThrow(NotFoundException::new);

        if (!passwordEncoder.matches(password, entity.getPassword())){
            throw new CredentialNotMatchesException();
        }

        return new LoginAuthentification(login, entity.getRoles());
    }

    public TokenAuthentification authentificate(String token) {
        return repository.findByTokin(token)
                .map(o -> new TokenAuthentification(o.getLogin(), o.getRoles()))
                .orElseThrow(TokenNotFoundException::new)
                ;
    }

    public UserLoginResponseDTO login(UserLoginRequestDTO requestData) {
        final UserEntity entity = repository.findByLogin(requestData.getLogin()).orElseThrow(NotFoundException::new);

        if (!passwordEncoder.matches(requestData.getPassword(), entity.getPassword())){
            throw new CredentialNotMatchesException();
        }
        final String token = createToken(entity);

        return new UserLoginResponseDTO(entity.getId(), entity.getLogin(), token);

    }

    private String createToken(UserEntity saved) {
        byte[] buffer = new byte[128];
        random.nextBytes(buffer);
        final String token = Base64.getUrlEncoder().withoutPadding().encodeToString(buffer);
        repository.save(new TokenEntity(saved.getId(), token));
        return token;
    }

    public UserCreateResponseDTO create(Authentication auth, UserCreateRequestDTO requestData) {
        if (!auth.hasRole(Roles.USERS_EDIT_ALL)){
            throw new ForbiddenException();
        }

        final String hashedPassword = passwordEncoder.encode(requestData.getPassword());
        repository.findByLogin(requestData.getLogin()).ifPresent(o -> {
            throw new UsernameAlreadyRegisteredException(o.getLogin());
        });
        final UserEntity saved = repository.save(new UserEntity(
                0L,
                requestData.getLogin(),
                hashedPassword,
                requestData.getRoles()
        ));

        return new UserCreateResponseDTO(saved.getId(), saved.getLogin());
    }

    public UserChangeRolesResponseDTO changeRoles(Authentication auth, UserChangeRolesRequestDTO requestData) {
        if (!auth.hasRole(Roles.USERS_EDIT_ALL)){
            throw new ForbiddenException();
        }
        final UserEntity entity = repository.setRolesByLogin(requestData.getLogin(), requestData.getRoles());
        return new UserChangeRolesResponseDTO(entity.getId(), entity.getLogin());
    }
}
