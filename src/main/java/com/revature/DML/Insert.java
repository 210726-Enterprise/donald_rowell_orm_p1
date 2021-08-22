package com.revature.DML;

import com.revature.DDL.Create;
import com.revature.model.BasicModel;
import com.revature.model.ColumnField;
import com.revature.util.ConnectionFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class Insert<T>{

    private T obj;
    private BasicModel<T> model;

    public Insert(T obj, BasicModel<T> model) {
        this.obj = obj;
        this.model = model;
    }

    public int insert(){
        String tableName = model.getTableName();
        String sqlId = "SELECT MAX(" + model.getPrimaryKey().getColumnName() + ") FROM " + tableName + ";";
        String sql = "INSERT INTO " + tableName + " (";
        List<ColumnField> colFields = model.getColumnFields();
        if(colFields.isEmpty()){
            throw new IllegalStateException("No columns present when attempting to insert into table for given model: " + model.getClass());
        }
        int count = 0;
        String[] fieldNames = new String[colFields.size()];
        for(ColumnField colField : colFields){
            sql = sql.concat(colField.getColumnName()+",");
            fieldNames[count] = colField.getFieldName();
            count++;
        }
        sql = sql.substring(0,sql.lastIndexOf(','));
        sql = sql.concat(") VALUES (");
        for(int i = 1; i < count; i++){
            sql = sql.concat("?,");
        }
        sql = sql.concat("?);");

        try(Connection connection = ConnectionFactory.getConnection()){
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null,null,tableName,null);
            if(!tables.next()){
                Create.createTable(tableName, model.getPrimaryKey().getColumnName(), colFields);
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            for(int i = 1; i <= count; i++){
                int finalI = i;
                ps.setObject(i,Arrays.stream(obj.getClass().getDeclaredMethods())
                        .filter(m -> m.getName().equalsIgnoreCase("get" + fieldNames[finalI]))
                        .findFirst().orElseThrow(RuntimeException::new)
                        .invoke(obj));
            }

            ps.execute();

            PreparedStatement psId = connection.prepareStatement(sqlId);
            ResultSet rs = psId.executeQuery();

            if(rs.next()){
                return rs.getInt(1);
            }

        } catch (SQLException e){
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
