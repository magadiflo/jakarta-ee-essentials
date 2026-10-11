package dev.magadiflo.app.config;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ProducerResources {

    private static final Logger log = Logger.getLogger(ProducerResources.class.getName());

    @Resource(name = "jdbc/MySQLResource")
    private DataSource dataSource;

    @Produces
    @RequestScoped
    @Named("connection")
    private Connection connection() throws NamingException, SQLException {
        log.info("Estableciendo conexión a la base de datos");
        return this.dataSource.getConnection();
    }
}
