package com.revature.DML;

import com.revature.ORM;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete extends ORM {

    public static boolean delete(int id, String tableName, String pk){
        String sql = "DELETE FROM " + tableName + " WHERE " + pk + " = ?;";

        try(Connection connection = ConnectionFactory.getConnection(ORM.db)){
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1,id);

            ps.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}