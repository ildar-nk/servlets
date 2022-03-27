package org.example.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.attribute.RequestAttributes;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.regex.Pattern;

public class X509AuthentificationFilter extends HttpFilter {
    private final Pattern pattern = Pattern.compile("CN=(.*?)(?:,|$)");

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Object attribute = req.getAttribute(RequestAttributes.X509_ATTR);
        X509Certificate[] x509Certificates = (X509Certificate[]) attribute;
        chain.doFilter(req, res);


    }
}
