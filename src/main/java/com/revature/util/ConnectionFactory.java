package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton used to set up the connection between this java code and the database.
 */
public class ConnectionFactory {

    private static Connection connection;

    public static Connection getConnection(String[] db) {

        try {
            connection = DriverManager.getConnection(db[0],db[1],db[2]);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

}
