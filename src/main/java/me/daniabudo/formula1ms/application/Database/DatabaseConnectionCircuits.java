package me.daniabudo.formula1ms.application.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionCircuits {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/circuits_schema";
    private static final String USER = "root";
    private static final String PASSWORD = "D@niaUVT";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

