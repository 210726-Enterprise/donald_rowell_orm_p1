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
            connection = DriverManager.getConnection(System.getenv("db_url"),System.getenv("db_username"),System.getenv("db_password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

}
