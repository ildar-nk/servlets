package org.example.security;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginAuthentification implements Authentication{
    private final String login;
    private final String[] roles;

    @Override
    public String[] getRoles() {
        return roles;
    }

    @Override
    public String getname() {
        return login;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }
}
