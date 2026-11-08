package dev.magadiflo.app.annotations;

import dev.magadiflo.app.interceptor.logging.Logging;
import dev.magadiflo.app.interceptor.transactional.TransactionalJdbc;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Stereotype;
import jakarta.inject.Named;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Stereotype
@TransactionalJdbc // 👈 nuevo: transacción en todos los métodos del servicio
@Logging
@ApplicationScoped
@Named
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {
}
