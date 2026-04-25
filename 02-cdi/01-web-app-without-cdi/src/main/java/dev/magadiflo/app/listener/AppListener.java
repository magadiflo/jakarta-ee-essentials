package dev.magadiflo.app.listener;

import dev.magadiflo.app.model.ShoppingCart;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class AppListener implements ServletContextListener, ServletRequestListener, HttpSessionListener {

    private ServletContext servletContext;

    // ── Contexto de aplicación ──────────────────────────────────────────────
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Se ejecuta UNA vez al arrancar Tomcat — ideal para inicialización global
        this.servletContext = sce.getServletContext();
        this.servletContext.log("Aplicacion inicializada!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Se ejecuta UNA vez al apagar Tomcat — ideal para liberar recursos globales
        this.servletContext.log("Aplicacion destruida!");
    }

    // ── Request ─────────────────────────────────────────────────────────────
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        // Se ejecuta al INICIO de cada petición HTTP — antes de llegar al servlet
        this.servletContext.log("Iniciando request!");
        sre.getServletRequest().setAttribute("title", "Jakarta EE Essentials");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        // Se ejecuta al FIN de cada petición HTTP — después de que el servlet responde
        this.servletContext.log("Destruyendo el request!");
    }

    // ── Sesión HTTP ─────────────────────────────────────────────────────────
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // Se ejecuta cuando Tomcat crea una nueva sesión HTTP para un usuario
        this.servletContext.log("Session HTTP inicializada!");
        se.getSession().setAttribute("shopping-cart", new ShoppingCart());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // Se ejecuta cuando la sesión expira o se invalida con session.invalidate()
        this.servletContext.log("Sesión HTTP destruida!");
    }
}
