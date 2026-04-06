package dev.magadiflo.app.service;

import dev.magadiflo.app.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> login(String username, String password);
}
