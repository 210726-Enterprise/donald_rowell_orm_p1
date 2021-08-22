package com.revature.DDL;

import com.revature.model.ColumnField;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Create {

    public static void createTable(String tableName, String pk, List<ColumnField> columnFields) throws SQLException {
        String sql = "CREATE TABLE " + tableName
                + "( " + pk + " SERIAL PRIMARY KEY, ";

        for(ColumnField col : columnFields){
            sql = sql.concat(col.getColumnName() + " " + columnType(col.getFieldType()) + ", ");
        }

        sql = sql.substring(0, sql.lastIndexOf(','));

        sql = sql.concat(");");

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.execute();
        connection.close();
    }

    private static String columnType(Class<?> fieldType){
        if(fieldType.getSimpleName().contains("String")){
            return "VARCHAR";
        }
        else if(fieldType.getSimpleName().contains("Boolean")){
            return "BOOLEAN";
        } else{
            return "REAL";
        }
    }
}
