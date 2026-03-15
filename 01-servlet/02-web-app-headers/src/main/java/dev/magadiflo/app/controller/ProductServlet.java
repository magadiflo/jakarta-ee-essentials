package dev.magadiflo.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.magadiflo.app.service.ProductService;
import dev.magadiflo.app.service.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Capa de servicio — obtiene los datos
        ProductService productService = new ProductServiceImpl();

        // ObjectMapper (Jackson) — convierte la lista de objetos Java a JSON String
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProducts = objectMapper.writeValueAsString(productService.findAllProducts());

        // Indicamos al cliente que la respuesta es JSON
        resp.setContentType("application/json");
        resp.getWriter().write(jsonProducts);
    }
}
