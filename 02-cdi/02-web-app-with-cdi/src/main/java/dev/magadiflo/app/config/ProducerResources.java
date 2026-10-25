package dev.magadiflo.app.config;

import dev.magadiflo.app.annotations.MySQLConnection;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@ApplicationScoped
public class ProducerResources {

    private static final Logger log = Logger.getLogger(ProducerResources.class.getName());

    @Resource(name = "jdbc/MySQLResource")
    private DataSource dataSource;

    @Produces
    @RequestScoped
    @MySQLConnection
    private Connection connection() throws NamingException, SQLException {
        log.info("Estableciendo conexión a la base de datos");
        return this.dataSource.getConnection();
    }

    public void close(@Disposes @MySQLConnection Connection connection) throws SQLException {
        connection.close();
        log.info("Cerrando conexión a la base de datos MySQL");
    }
}
