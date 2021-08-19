package com.revature.DML;

import com.revature.ORM;
import com.revature.annotations.Table;
import com.revature.model.BasicModel;
import com.revature.model.ColumnField;
import com.revature.util.ConnectionFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Insert<T> extends ORM<T> {

    private T obj;
    private BasicModel<T> model;

    public Insert(T obj, BasicModel<T> model) {
        this.obj = obj;
        this.model = model;
    }

    public int insert(){
        String tableName = model.getClass().getAnnotation(Table.class).tableName();
        String sqlId = "SELECT MAX(" + model.getPrimaryKey().getColumnName() + ") FROM " + tableName + ";";
        String sql = "INSERT INTO " + tableName + " (";
        List<ColumnField> colFields = model.getColumnFields();
        if(colFields.isEmpty()){
            throw new IllegalStateException("No columns present to be inserted into table for given model: " + model.getClass());
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

        try(Connection connection = ConnectionFactory.getConnection(db)){
            PreparedStatement ps = connection.prepareStatement(sql);
            for(int i = 0; i < count; i++){
                int finalI = i;
                ps.setObject(i,Arrays.stream(obj.getClass().getDeclaredMethods())
                        .filter(m -> m.getName().equals("get" + fieldNames[finalI]))
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
/*
        String sql = "INSERT INTO customer_information (user_name, password, first_name, last_name, birth_date, phone, email) VALUES (?,?,?,?,?,?,?)";
        String sqlID = "SELECT MAX(customer_id) FROM customer_information;";

        Integer custID = null;
        try(Connection connection = ConnectionFactory.getConnection()){

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,ci.getUserName());
            ps.setString(2,ci.getPassword());
            ps.setString(3,ci.getfName());
            ps.setString(4,ci.getlName());
            ps.setDate(5,ci.getBirthDate());
            ps.setString(6,ci.getPhoneNum());
            ps.setString(7,ci.getEmail());

            ps.execute();

            PreparedStatement psID = connection.prepareStatement(sqlID);
            ResultSet rs = psID.executeQuery();
            while(rs.next()) {
                custID = rs.getInt(1);
            }
        }catch(SQLException e){
            // logging
        }
        return custID;

 */
    }

}
