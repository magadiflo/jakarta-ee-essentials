package dev.magadiflo.app.config;

import dev.magadiflo.app.annotations.MySQLConnection;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@ApplicationScoped
public class ProducerResources {

    // Esta clase produce el Logger y también la podemos inyectar
    @Inject
    private Logger log;

    @Resource(name = "jdbc/MySQLResource")
    private DataSource dataSource;

    @Produces
    @RequestScoped
    @MySQLConnection
    private Connection connection() throws SQLException {
        log.info("Estableciendo conexión a la base de datos");
        return this.dataSource.getConnection();
    }

    //Como no le definimos un scope, por defecto será @Dependent, es decir, su alcance
    //será de acuerdo al alcance de la clase donde será inyectado.
    @Produces // Registramos en el Contenedor CDI
    private Logger beanLogger(InjectionPoint injectionPoint) {
        //InjectionPoint: entrega información de la clase en la que inyectaremos este bean, es decir,
        //proporciona acceso a metadatos sobre un punto de inyección. Por ejemplo, si lo inyectamos en
        //la clase ShoppingCart, pues nos dará información sobre esa clase.
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    public void close(@Disposes @MySQLConnection Connection connection) throws SQLException {
        connection.close();
        log.info("Cerrando conexión a la base de datos MySQL");
    }
}
