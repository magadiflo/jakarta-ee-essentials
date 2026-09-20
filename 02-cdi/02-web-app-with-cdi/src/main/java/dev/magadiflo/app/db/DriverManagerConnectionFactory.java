package dev.magadiflo.app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DriverManagerConnectionFactory {

    private static final Logger log = Logger.getLogger(DriverManagerConnectionFactory.class.getName());

    private static final String URL = "jdbc:mysql://localhost:3306/db_jakarta_ee_essentials?serverTimezone=America/Lima";
    private static final String USER = "root";
    private static final String PASSWORD = "magadiflo";

    private DriverManagerConnectionFactory() {
        // Evita instanciación
    }

    public static Connection getConnection() throws SQLException {
        log.info("Estableciendo conexión a la base de datos");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
