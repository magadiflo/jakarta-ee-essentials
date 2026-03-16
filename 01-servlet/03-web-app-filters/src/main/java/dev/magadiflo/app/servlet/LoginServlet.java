package dev.magadiflo.app.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.magadiflo.app.dto.LoginRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // De-serializar el body JSON → objeto LoginRequest
        LoginRequest loginRequest = objectMapper.readValue(req.getInputStream(), LoginRequest.class);

        // Validación simple
        boolean successLogin = "admin".equals(loginRequest.username()) && "1234".equals(loginRequest.password());

        resp.setContentType("application/json");
        if (successLogin) {
            resp.setStatus(HttpServletResponse.SC_OK); // 200
            String json = objectMapper.writeValueAsString(Map.of(
                    "message", "Login exitoso",
                    "username", loginRequest.username()
            ));
            resp.getWriter().write(json);

        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            String json = objectMapper.writeValueAsString(Map.of(
                    "message", "Credenciales incorrectas"
            ));
            resp.getWriter().write(json);
        }
    }
}
