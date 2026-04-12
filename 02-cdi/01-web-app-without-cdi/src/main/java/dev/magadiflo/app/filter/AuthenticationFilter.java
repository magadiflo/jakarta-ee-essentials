package dev.magadiflo.app.filter;

import dev.magadiflo.app.service.LoginService;
import dev.magadiflo.app.service.impl.LoginServiceSessionImpl;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter({"/carts/*", "/products/form/*", "/products/delete/*"})
public class AuthenticationFilter implements Filter {

    private final LoginService loginServiceSession = new LoginServiceSessionImpl();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (this.isAuthenticated(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Debe estar autenticado.");
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        return this.loginServiceSession.getUsername(request).isPresent();
    }
}
