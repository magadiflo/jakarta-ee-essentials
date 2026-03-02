# 🌐 Jakarta EE - Fundamentos esenciales

Este repositorio reúne los **fundamentos esenciales de Jakarta EE** que todo desarrollador Java debería conocer.  
El objetivo es construir una base sólida sobre los estándares de Jakarta antes de dar el salto a frameworks modernos
como Quarkus.

---

## 📌 ¿Qué es Jakarta EE?

`Jakarta EE (Enterprise Edition)` es el conjunto de especificaciones abiertas y estandarizadas para el desarrollo de
aplicaciones empresariales en Java. Es el sucesor de `Java EE` y define APIs que permiten construir aplicaciones
`portables`, `escalables` y `mantenibles`, desplegadas sobre un servidor de aplicaciones.

![01.png](assets/01-introduccion/01.png)

### 🏗️ Contenedores dentro de un servidor de aplicaciones

Un servidor de aplicaciones `Jakarta EE` se organiza en `contenedores`, que gestionan distintos aspectos de la
aplicación:

- 🌐 `Contenedor Web`
    - Maneja **Servlets** (controladores que procesan peticiones HTTP).
    - Gestiona **JSP (JavaServer Pages)** como vistas dinámicas.
    - Es la puerta de entrada de las solicitudes web.

- ⚙️ `Contenedor EJB (Enterprise JavaBeans)`
    - Históricamente alojaba la **lógica de negocio** y la **persistencia**.
    - Permitía servicios distribuidos y transacciones complejas.
    - Hoy en día, gran parte de sus responsabilidades han sido reemplazadas por **CDI** y **JPA**, más simples y
      modernos.

### 🔗 Interacción entre contenedores

- El **contenedor web** interactúa con el **contenedor EJB** para acceder a la capa de servicio y persistencia.
- La capa de servicio puede ser **local** (dentro de la misma aplicación) o **remota/distribuida**, consumiendo
  servicios REST en otros servidores.

### ✅ Nota práctica

> Aunque EJB fue muy importante en el pasado, en el ecosistema moderno (incluido Quarkus) su rol ha
> sido reemplazado por **CDI, JPA y JTA**. Por eso, entender la arquitectura histórica es útil como contexto, pero no
> necesitas profundizar en EJB para tu roadmap.

---

## ⚖️ Tomcat vs. WildFly

En el ecosistema Java existen distintos tipos de servidores. Entender sus diferencias es clave para elegir el más
adecuado según el contexto.

### 🐱 Apache Tomcat

- Es un `servidor de servlets`(*Servlet Container*).
- Implementa las especificaciones básicas de **Jakarta Servlet**, **JSP** y **WebSocket**.
- Ligero y ampliamente adoptado para aplicaciones web y APIs REST.
- No cubre todas las especificaciones de Jakarta EE (no soporta EJB, JMS, etc.).
- Compatible con **JAX-RS, CDI, JPA, Bean Validation y JTA** mediante dependencias externas,
  que son las especificaciones clave para trabajar con Quarkus.

### 🦅 WildFly

- Es un **servidor de aplicaciones completo** (*Application Server*).
- Implementa **toda la especificación Jakarta EE**: EJB, JMS, JAX-RS, CDI, JPA, entre otros.
- Más pesado y complejo que Tomcat; orientado a aplicaciones empresariales tradicionales.
- Resulta útil cuando se necesitan tecnologías como EJB, menos comunes en frameworks modernos.

### 📌 Conclusión práctica

Para un repaso de Jakarta EE enfocado en Quarkus, **Tomcat es suficiente**: cubre todas las especificaciones
relevantes, es ligero y elimina la complejidad innecesaria.

**WildFly** es la opción si en el futuro quisieras profundizar en EJB u otras especificaciones del stack completo,
pero queda fuera del objetivo actual.


