package dev.magadiflo.app.filter;

import dev.magadiflo.app.db.DataSourceConnectionFactory;
import dev.magadiflo.app.exception.DatabaseException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebFilter("/*") //El patrón definido indica que se aplicará a cualquier ruta
public class DatabaseConnectionFilter implements Filter {

    private static final Logger log = Logger.getLogger(DatabaseConnectionFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try (Connection connection = DataSourceConnectionFactory.getConnection()) {

            // Desactivamos autoCommit para manejar la transacción manualmente
            if (connection.getAutoCommit()) {
                log.info("Desactivando autoCommit");
                connection.setAutoCommit(false);
            }

            try {
                // Compartimos la conexión con el servlet vía atributo del request
                log.info("Agregando 'connection' al request");
                request.setAttribute("connection", connection);

                chain.doFilter(request, response); // Continuamos con la cadena de filtros hasta llegar al Servlet

                log.info("Realizando 'commit' en la base de datos");
                connection.commit(); // tod OK → confirmamos cambios
            } catch (SQLException | DatabaseException ex) {
                log.warning("Realizando 'rollback' en la base de datos");
                connection.rollback(); // algo falló → revertimos cambios

                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
                ex.printStackTrace();
            }
        } catch (SQLException | NamingException ex) {
            ex.printStackTrace(); // fallo al obtener la conexión
        }
    }
}
