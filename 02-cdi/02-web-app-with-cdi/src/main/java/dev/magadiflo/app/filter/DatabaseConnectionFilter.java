package dev.magadiflo.app.filter;

import dev.magadiflo.app.annotations.MySQLConnection;
import dev.magadiflo.app.exception.DatabaseException;
import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebFilter("/*") //El patrón definido indica que se aplicará a cualquier ruta
public class DatabaseConnectionFilter implements Filter {

    private static final Logger log = Logger.getLogger(DatabaseConnectionFilter.class.getName());

    @Inject
    @MySQLConnection
    private Connection connection;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // El filtro solo quedaría para el manejo de las transacciones. Para cerrar la conexión lo haríamos
        // en la clase ProducerResources con la anotación @Disposes
        try {
            // Desactivamos autoCommit para manejar la transacción manualmente
            if (this.connection.getAutoCommit()) {
                log.info("Desactivando autoCommit");
                this.connection.setAutoCommit(false);
            }

            chain.doFilter(request, response);

            log.info("Realizando 'commit' en la base de datos");
            this.connection.commit(); // tod OK → confirmamos cambios

        } catch (SQLException | DatabaseException ex) {
            log.warning("Realizando 'rollback' en la base de datos");
            try {
                this.connection.rollback(); // algo falló → revertimos cambios
            } catch (SQLException rollbackEx) {
                ex.addSuppressed(rollbackEx);
            }

            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
            ex.printStackTrace();
        }
    }
}
