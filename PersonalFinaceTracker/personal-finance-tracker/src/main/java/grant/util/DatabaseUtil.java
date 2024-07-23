package grant.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    static final String HOST = "localhost";
    static final int PORT = 3306;
    private static final String USER = "root";
    private static final String PASSWORD = "";
    static final String DB_NAME = "Staff";

    private Connection getConnection() throws SQLException {
        String url = getSqlUrl(HOST, PORT, DB_NAME);
        
        return DriverManager.getConnection(url, USER, PASSWORD);
    }

    /** Helper function to format a SQL URL string */
    public static String getSqlUrl(String host, int port, String dbName) {
        return String.format("jdbc:mysql://%s:%d/%s", host, port, dbName);
    }
}
