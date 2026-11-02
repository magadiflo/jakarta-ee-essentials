package dev.magadiflo.app.interceptor;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.util.logging.Logger;

@Logging
@Interceptor
public class LoggingInterceptor {

    @Inject
    private Logger log;

    //El nombre de este método puede ser cualquier nombre
    //InvocationContext: objeto que representa la invocación del método y va a contener toda la información del método
    //que se está interceptando (nombre del método, nombre de la clase del método, componente cdi, etc.)
    @AroundInvoke
    public Object logging(InvocationContext invocationContext) throws Exception {
        log.info("Entrando antes de invocar al método: "
                 + invocationContext.getMethod().getName()
                 + ", de la clase: " + invocationContext.getMethod().getDeclaringClass().getName());

        Object result = invocationContext.proceed(); // invocación del método

        log.info("Saliendo de la invocación del método: "
                 + invocationContext.getMethod().getName()
                 + ", de la clase: " + invocationContext.getMethod().getDeclaringClass().getName());

        return result;
    }
}
