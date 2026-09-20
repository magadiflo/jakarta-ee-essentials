package dev.magadiflo.app.service.impl;

import dev.magadiflo.app.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginServiceSessionImpl implements LoginService {
    @Override
    public Optional<String> getUsername(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Optional.ofNullable((String) session.getAttribute("username"));
    }
}
