package com.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/aetherbane";
        String user = "root";
        String password = "";
    
        Connection conn = DriverManager.getConnection(url, user, password);
        if (conn != null) {
            System.out.println("Database connected successfully.");
        } else {
            System.out.println("Failed to connect to the database.");
        }
        return conn;
    }
    
}
