package dev.magadiflo.app.interceptor.transactional;

import dev.magadiflo.app.annotations.MySQLConnection;
import dev.magadiflo.app.exception.DatabaseException;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.sql.Connection;
import java.util.logging.Logger;

@TransactionalJdbc
@Interceptor
public class TransactionalInterceptor {

    @Inject
    private Logger log;

    @Inject
    @MySQLConnection
    private Connection connection;

    @AroundInvoke
    public Object transactional(InvocationContext invocationContext) throws Exception {
        try {
            if (this.connection.getAutoCommit()) {
                log.info("Desactivando autoCommit");
                this.connection.setAutoCommit(false);
            }

            String initMessage = "Iniciando transacción. Método: %s, Clase: %s"
                    .formatted(invocationContext.getMethod().getName(),
                            invocationContext.getMethod().getDeclaringClass().getName());
            log.info(initMessage);

            Object result = invocationContext.proceed();

            log.info("Realizando 'commit' en la base de datos.");
            this.connection.commit(); // tod OK → confirmamos cambios

            log.info("Transacción finalizada.");
            return result;
        } catch (DatabaseException ex) {
            log.warning("Realizando 'rollback' en la base de datos");
            this.connection.rollback();
            throw ex; // Volvemos a lanzar el DatabaseException, que luego será capturada en el filtro DatabaseExceptionFilter
        }
    }
}
