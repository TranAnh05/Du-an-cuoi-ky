package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/transaction?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    private Connection conn;

    public DBConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Explicitly load the driver
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public Connection getConnection() {
        return conn;
    }

    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}