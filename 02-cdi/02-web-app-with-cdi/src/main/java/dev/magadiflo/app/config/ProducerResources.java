package dev.magadiflo.app.config;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ProducerResources {

    private static final Logger log = Logger.getLogger(ProducerResources.class.getName());

    @Produces
    @RequestScoped
    @Named("connection")
    private Connection connection() throws NamingException, SQLException {
        log.info("Estableciendo conexión a la base de datos");
        // Paso 1 — accedemos al contexto raíz de JNDI
        Context initContext = new InitialContext();
        // Paso 2 — navegamos al subcontexto de recursos de la aplicación
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        // Paso 3 — buscamos el DataSource por su nombre JNDI (definido en context.xml)
        DataSource ds = (DataSource) envContext.lookup("jdbc/MySQLResource");
        // Paso 4 — pedimos una conexión al pool
        return ds.getConnection();
    }
}
