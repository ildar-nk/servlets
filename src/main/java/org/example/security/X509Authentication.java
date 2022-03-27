package org.example.security;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class X509Authentication implements Authentication {
    private final String commonName;

    @Override
    public String getname() {
        return commonName;
    }

    @Override
    public boolean isAnonymous() {
        return false;
    }
}
