package org.example.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.attribute.RequestAttributes;
import org.example.security.Authentication;
import org.example.security.AuthenticationException;
import org.example.security.X509Authentication;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class X509AuthentificationFilter extends HttpFilter {
    private final Pattern pattern = Pattern.compile("CN=(.*?)(?:,|$)");

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Authentication existing = (Authentication) req.getAttribute(RequestAttributes.AUTH_ATTR);
        if (existing != null && !existing.isAnonymous()){
            chain.doFilter(req, res);
            return;
        }
        final Object attribute = req.getAttribute(RequestAttributes.X509_ATTR);
        if (attribute == null){
            chain.doFilter(req, res);
            return;
        }
        try {
            final X509Certificate[] x509Certificates = (X509Certificate[]) attribute;
            final X509Certificate certificate = x509Certificates[0];
            final String name = certificate.getSubjectX500Principal().getName();
            final Matcher matcher = pattern.matcher(name);
            if (!matcher.find()) {
                throw new AuthenticationException("can't find");
            }
            final String commonName = matcher.group(1);
            req.setAttribute(RequestAttributes.AUTH_ATTR, new X509Authentication(commonName));
        } catch (AuthenticationException e){
            e.printStackTrace();
            res.sendError(401);
            return;
        }

        chain.doFilter(req, res);


    }
}
