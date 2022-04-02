package org.example.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.attribute.ContexAttributes;
import org.example.attribute.RequestAttributes;
import org.example.exception.BadCredentialFormatException;
import org.example.security.Authentication;
import org.example.security.AuthenticationException;
import org.example.security.LoginAuthentification;
import org.example.security.TokenAuthentification;
import org.example.service.UserService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BearerAuthenticationFilter extends HttpFilter {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(ContexAttributes.AUTHENTICATOR_ATTR);
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        final Authentication existing = (Authentication) req.getAttribute(RequestAttributes.AUTH_ATTR);
        if (existing != null && !existing.isAnonymous()){
            chain.doFilter(req, res);
            return;
        }
        final String header = req.getHeader("Authorization");
        if (header == null) {
            chain.doFilter(req, res);
            return;
        }
        if (!header.startsWith("Bearer")){
            chain.doFilter(req, res);
            return;
        }
        try {
            final String token = header.substring("Bearer".length() + 1);
            final TokenAuthentification authentification = userService.authentificate(token);
            req.setAttribute(RequestAttributes.AUTH_ATTR, authentification);


        } catch (AuthenticationException e){

        }


        chain.doFilter(req, res);

    }
}
