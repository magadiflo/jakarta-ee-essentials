package dev.magadiflo.app.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet("/http-headers")
public class HttpHeadersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- Información de la URL y el servidor ---
        String metodoHttp = request.getMethod();                // GET, POST, PUT...
        String requestUri = request.getRequestURI();            // /02-web-app-headers/http-headers
        String requestUrl = request.getRequestURL().toString(); // URL completa con protocolo
        String contextPath = request.getContextPath();          // /02-web-app-headers
        String servletPath = request.getServletPath();          // /http-headers
        String ip = request.getLocalAddr();                     // IP del servidor
        int port = request.getLocalPort();                      // Puerto del servidor
        String schema = request.getScheme();                    // http / https

        // --- Información del cliente ---
        String host = request.getHeader("host");          // localhost:8080
        String ipCliente = request.getRemoteAddr();             // IP del cliente

        // Construimos la URL completa manualmente para ilustrar sus partes
        String urlCompleta = schema + "://" + host + contextPath + servletPath;

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) { // try-with-resources: cierra automáticamente
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("   <head>");
            out.println("       <meta charset=\"UTF-8\">");
            out.println("       <title>Cabeceras HttpRequest</title>");
            out.println("   </head>");
            out.println("   <body>");
            out.println("       <ul>");
            out.println("           <li>metodoHttp: " + metodoHttp + "</li>");
            out.println("           <li>requestUri: " + requestUri + "</li>");
            out.println("           <li>requestUrl: " + requestUrl + "</li>");
            out.println("           <li>contextPath: " + contextPath + "</li>");
            out.println("           <li>servletPath: " + servletPath + "</li>");
            out.println("           <li>ip local: " + ip + "</li>");
            out.println("           <li>puerto local: " + port + "</li>");
            out.println("           <li>schema: " + schema + "</li>");
            out.println("           <li>host: " + host + "</li>");
            out.println("           <li>url completa: " + urlCompleta + "</li>");
            out.println("           <li>id cliente: " + ipCliente + "</li>");

            // Iteramos TODAS las cabeceras HTTP que envió el navegador
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String cabecera = headerNames.nextElement();
                out.println("<li>" + cabecera + ": " + request.getHeader(cabecera) + "</li>");
            }

            out.println("       </ul>");
            out.println("   </body>");
            out.println("</html>");
        }
    }
}
