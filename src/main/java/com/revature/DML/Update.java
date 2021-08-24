package com.revature.DML;

import com.revature.model.BasicModel;
import com.revature.model.ColumnField;
import com.revature.util.ConnectionFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class Update<T>{

    private T obj;
    private BasicModel<T> model;

    public Update(T obj, BasicModel<T> model){
        this.obj = obj;
        this.model = model;
    }

    public boolean update() throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "UPDATE " + model.getTableName() + " SET ";
        int count = 0;
        String[] fieldNames = new String[model.getColumnFields().size()];
        for(ColumnField columnField: model.getColumnFields()){
            sql = sql.concat(columnField.getColumnName() + " = ?, ");
            fieldNames[count] = columnField.getFieldName();
            count++;
        }
        sql = sql.substring(0,sql.lastIndexOf(','));
        sql = sql.concat(" WHERE " + model.getPrimaryKey().getColumnName() + " = ?;");

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        for(int i = 1; i <= count; i++){
            int finalI = i-1;
            ps.setObject(i, Arrays.stream(obj.getClass().getDeclaredMethods())
                    .filter(m -> m.getName().equalsIgnoreCase("get" + fieldNames[finalI]))
                    .findFirst().orElseThrow(RuntimeException::new)
                    .invoke(obj));
        }

        ps.setInt(count+1, (Integer) Arrays.stream(obj.getClass().getDeclaredMethods())
                .filter(m -> m.getName().equalsIgnoreCase("get" + model.getPrimaryKey().getFieldName()))
                .findFirst().orElseThrow(RuntimeException::new)
                .invoke(obj));

        int success = ps.executeUpdate();

        connection.close();
        return success > 0;
    }
}
