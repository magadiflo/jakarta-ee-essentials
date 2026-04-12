package dev.magadiflo.app.controller;

import dev.magadiflo.app.model.User;
import dev.magadiflo.app.service.LoginService;
import dev.magadiflo.app.service.UserService;
import dev.magadiflo.app.service.impl.LoginServiceSessionImpl;
import dev.magadiflo.app.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Optional;

@WebServlet({"/login", "/login.html"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        LoginService loginService = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = loginService.getUsername(req);
        if (usernameOptional.isPresent()) {
            String username = usernameOptional.get();

            resp.setContentType("text/html;charset=utf-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("""
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        """);
                out.println("<title>Hola %s</title>".formatted(username));
                out.println("""
                        </head>
                        <body>
                        """);
                out.println("<h1>Hola %s, has iniciado sesión con éxito</h1>".formatted(username));
                out.println("<p><a href='" + req.getContextPath() + "/index.jsp'>volver</a></p>");
                out.println("<p><a href='" + req.getContextPath() + "/logout'>cerrar sesión</a></p>");
                out.println("""
                            </body>
                        </html>
                        """);
            }
        } else {
            req.setAttribute("title", req.getAttribute("title") + ": Login");
            this.getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserService service = new UserServiceImpl((Connection) req.getAttribute("connection"));
        Optional<User> loginOptional = service.login(username, password);

        if (loginOptional.isPresent()) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "/login.html");
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: No se pudo autenticar correctamente");
        }
    }
}
