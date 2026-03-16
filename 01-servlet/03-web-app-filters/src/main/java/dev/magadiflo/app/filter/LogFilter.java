package dev.magadiflo.app.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.logging.Logger;

@WebFilter("/*") // Aplica a TODAS las URLs de la aplicación
public class LogFilter implements Filter {

    private static final Logger log = Logger.getLogger(LogFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("[LogFilter] Filter inicializado");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // --- Antes del servlet ---
        String message = "[LogFilter] >>>> Solicitud entrante: %s %s".formatted(httpRequest.getMethod(), httpRequest.getRequestURI());
        LogFilter.log.info(message);

        // Pasamos la petición al siguiente eslabón (otro filter o el servlet)
        chain.doFilter(request, response);

        // --- DESPUÉS del servlet ---
        message = "[LogFilter] <<< Solicitud completada: %s %s".formatted(httpRequest.getMethod(), httpRequest.getRequestURI());
        LogFilter.log.info(message);
    }
}
