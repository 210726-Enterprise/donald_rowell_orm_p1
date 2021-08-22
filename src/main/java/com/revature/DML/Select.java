package com.revature.DML;

import com.revature.ORM;
import com.revature.annotations.Column;
import com.revature.annotations.Table;
import com.revature.model.BasicModel;
import com.revature.model.ColumnField;
import com.revature.util.ConnectionFactory;

import java.lang.reflect.*;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Select<T> extends ORM<T> {

    private String[] db;
    private BasicModel<T> model;
    private T o;

    public Select(String[] db, BasicModel<T> model) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        this.db = db;
        this.model = model;
        o = null;

        Constructor<?>[] constructors = o.getClass().getConstructors();

        o = (T) Arrays.stream(constructors)
                .filter(c -> c.getParameterTypes().length == 0) // grab the no arg constructor
                .findFirst().orElseThrow(RuntimeException::new).newInstance();
    }


    public T where(String col, Object val) throws Exception {

        String sql = "SELECT * FROM " + model.getTableName() + " WHERE " + col.toLowerCase() + " = ?;";

        Method[] methods = o.getClass().getMethods();

        try(Connection connection = ConnectionFactory.getConnection(db)){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1,val);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();

            if(rs.next()){

                Arrays.stream(methods)
                        .filter(setter -> setter.getName().equalsIgnoreCase("set"+model.getPrimaryKey().getFieldName()))
                        .findFirst().orElseThrow(RuntimeException::new)
                        .invoke(o,rs.getObject(1));
                for(ColumnField colField : model.getColumnFields()){
                    colField.getField().setAccessible(true);
                    colField.getField().set(o, rs.getObject(colField.getColumnName()));
                }
            }
        }catch(SQLException e){
            // logging
            throw new SQLException("Failed to get model given criteria.");
        }
        return o;
    }
/*
    public Optional<List<T>> all() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        String sql = "SELECT * FROM " + model.getClass().getAnnotation(Table.class).tableName() + ";";

        Method[] methods = o.getClass().getMethods();

        try(Connection connection = ConnectionFactory.getConnection(db)){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1,val);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();

            while(rs.next()){
                for(int i = 0; i < md.getColumnCount(); i++){
                    String columnName = md.getColumnName(i);
                    Arrays.stream(methods)
                            .filter(setter -> setter.getName().equals("set"+columnName))
                            .findFirst().orElseThrow(RuntimeException::new)
                            .invoke(o,rs.getObject(i));
                }
            }

        }catch(SQLException e){
            // logging
        }



        return o;
    }*/
}
