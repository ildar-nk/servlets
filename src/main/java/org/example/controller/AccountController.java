package org.example.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.attribute.RequestAttributes;
import org.example.dto.AccountGetAllResponseDTO;
import org.example.mime.ContentTypes;
import org.example.security.Authentication;
import org.example.service.AccountService;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;

@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;
    private final Gson gson;

    public void getAll(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        Authentication auth = (Authentication) req.getAttribute(RequestAttributes.AUTH_ATTR);
        final List<AccountGetAllResponseDTO> responseData = service.getAll(auth);
        resp.setContentType(ContentTypes.APPLICATION_JSON);
        resp.getWriter().write(gson.toJson(responseData));

    }
}
