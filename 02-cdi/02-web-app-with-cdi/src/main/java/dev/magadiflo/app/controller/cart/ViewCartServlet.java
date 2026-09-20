package dev.magadiflo.app.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/carts/view")
public class ViewCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("title", req.getAttribute("title") + ": Carro de compras");

        this.getServletContext()
                .getRequestDispatcher("/cart.jsp")
                .forward(req, resp);
    }
}
