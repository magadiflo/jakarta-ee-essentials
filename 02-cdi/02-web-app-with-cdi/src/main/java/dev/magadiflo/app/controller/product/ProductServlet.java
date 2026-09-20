package dev.magadiflo.app.controller.product;

import dev.magadiflo.app.model.Product;
import dev.magadiflo.app.service.LoginService;
import dev.magadiflo.app.service.ProductService;
import dev.magadiflo.app.service.impl.LoginServiceSessionImpl;
import dev.magadiflo.app.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@WebServlet({"/products", "/products.html"})
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtiene todos los productos desde la base de datos
        Connection connection = (Connection) req.getAttribute("connection");
        ProductService productService = new ProductServiceImpl(connection);
        List<Product> products = productService.getAllProducts();

        // Verifica si hay sesión activa
        LoginService loginService = new LoginServiceSessionImpl();
        Optional<String> optionalUsername = loginService.getUsername(req);

        // Pasa los datos a la vista
        req.setAttribute("products", products);
        req.setAttribute("username", optionalUsername);
        req.setAttribute("title", req.getAttribute("title") + ": Listado de productos");

        this.getServletContext()
                .getRequestDispatcher("/list.jsp")
                .forward(req, resp);
    }
}
