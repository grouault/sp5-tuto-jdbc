package com.exo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionUtil {

    private static String BDD_LOGIN = "root";
    private static String BDD_PWD = "gildas";
    private static String BDD_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String BDD_URL = "jdbc:mysql://localhost:3306/entreprise?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private static final Logger LOG = LogManager.getLogger();

    private  static ConnectionUtil instance;

    private Connection connection;

    static {
        try {
            Class.forName(BDD_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ConnectionUtil() {
    }

    public static ConnectionUtil getInstance() {
        if (instance == null) {
            instance = new ConnectionUtil();
        }
        return instance;
    }

    public Connection etablirConnexion() throws SQLException{
        if (this.connection == null || this.connection.isClosed()) {
            this.connection = DriverManager.getConnection(BDD_URL, BDD_LOGIN, BDD_PWD);
            ConnectionUtil.LOG.info("ouverture de connexion");
        } else {
            ConnectionUtil.LOG.info("connexion deja etablie");
        }
        return this.connection;
    }

    public void fermerConnexion() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
            ConnectionUtil.LOG.info("fermeture de la connexion");
        } else {
            ConnectionUtil.LOG.info("connexion deja ferme");
        }
    }

}
