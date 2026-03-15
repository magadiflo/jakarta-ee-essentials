package dev.magadiflo.app;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/parameters")
public class ParametersGetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Leer el parámetro "greetings" de la Query String
        String greetings = req.getParameter("greetings");

        // Si no se envió el parámetro, getParameter() retorna null.
        // En nuestro caso, definimos un valor por defecto en caso sea null.
        greetings = greetings == null ? "Texto por defecto (no se envió saludo)" : greetings;

        resp.setContentType("text/html;charset=utf-8");

        try (PrintWriter out = resp.getWriter()) {
            out.println("""
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Document</title>
                    </head>
                    <body>
                        <h1>Parámetros Get de la URL</h1>
                    """);
            out.println("<p><b>Saludo: </b>%s</p>".formatted(greetings));
            out.println("""
                        </body>
                    </html>
                    """);
        }
    }
}
