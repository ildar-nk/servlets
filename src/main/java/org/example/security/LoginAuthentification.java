package org.example.security;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginAuthentification implements Authentication{
    private final String login;


    @Override
    public String getname() {
        return login;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }
}
