package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton used to set up the connection between this java code and the database.
 */
public class ConnectionFactory {

    private static Connection connection;

    public static Connection getConnection() {

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://databank.cbu38mywfjcp.us-east-2.rds.amazonaws.com/postgres","postgres","password");
            System.out.println("Database connected");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to make connection");
        }

        return connection;
    }

}
