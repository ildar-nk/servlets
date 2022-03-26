package org.example.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserGetAllResponseDTO;
import org.example.service.UserService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final Gson gson;

    public void getAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final List<UserGetAllResponseDTO> responseData = service.getAll();
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(responseData));
    }
}
