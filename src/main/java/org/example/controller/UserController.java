package org.example.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.mime.ContentTypes;
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

    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final UserRegisterRequestDTO requestData = gson.fromJson(request.getReader(), UserRegisterRequestDTO.class);
        final UserRegisterResponseDTO responseData = service.register(requestData);
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseData));
    }
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException{
        final UserLoginRequestDTO requestData = gson.fromJson(
                request.getReader(),
                UserLoginRequestDTO.class);
        final UserLoginResponseDTO responseData = service.login(requestData);
        response.setContentType(ContentTypes.APPLICATION_JSON);
        response.getWriter().write(gson.toJson(responseData));
    }
}
