package org.example.security;

public class AnonymousAuthentication implements Authentication {

    public static final String ANONYMOUS = "anonymous";

    @Override
    public String getname() {
        return ANONYMOUS;
    }

    @Override
    public boolean isAnonymous() {
        return true;
    }
}
