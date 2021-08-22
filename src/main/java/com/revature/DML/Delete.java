package com.revature.DML;

import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete{

    public static boolean delete(int id, String tableName, String pk) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + pk + " = ?;";

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1,id);

        ps.execute();
        connection.close();
        return true;
    }
}
