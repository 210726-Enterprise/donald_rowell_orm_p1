package com.revature.DML;

import com.revature.model.BasicModel;
import com.revature.model.ColumnField;
import com.revature.util.ConnectionFactory;

import java.lang.reflect.*;
import java.sql.*;
import java.util.Arrays;

public class Select<T>{

    private BasicModel<T> model;
    private T o;

    public Select(BasicModel<T> model, Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        this.model = model;

        Constructor<?>[] constructors = clazz.getConstructors();

        o = (T) Arrays.stream(constructors)
                .filter(c -> c.getParameterCount() == 0) // grab the no arg constructor
                .findFirst().orElseThrow(RuntimeException::new).newInstance();
    }

    public T where(String col, int id) throws Exception {

        String sql = "SELECT * FROM " + model.getTableName() + " WHERE " + col.toLowerCase() + " = ?;";

        Method[] methods = o.getClass().getMethods();

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1,id);

        ResultSet rs = ps.executeQuery();
        ResultSetMetaData md = rs.getMetaData();

        if(rs.next()){

            Arrays.stream(methods)
                    .filter(setter -> setter.getName().equalsIgnoreCase("set"+model.getPrimaryKey().getFieldName()))
                    .findFirst().orElseThrow(RuntimeException::new)
                    .invoke(o,rs.getObject(1));
            for(ColumnField colField : model.getColumnFields()){
                if(rs.getObject(colField.getColumnName()).getClass().getSimpleName().equals("BigDecimal")){
                    Arrays.stream(methods)
                            .filter(setter -> setter.getName().equalsIgnoreCase("set"+colField.getFieldName()))
                            .findFirst().orElseThrow(RuntimeException::new)
                            .invoke(o, rs.getDouble(colField.getColumnName()));
                } else {
                    Arrays.stream(methods)
                            .filter(setter -> setter.getName().equalsIgnoreCase("set" + colField.getFieldName()))
                            .findFirst().orElseThrow(RuntimeException::new)
                            .invoke(o, rs.getObject(colField.getColumnName()));
                }
            }
        }
        connection.close();
        return o;
    }

    public T where(String col, Object val) throws Exception {

        String sql = "SELECT * FROM " + model.getTableName() + " WHERE " + col.toLowerCase() + " = ?;";

        Method[] methods = o.getClass().getMethods();

        Connection connection = ConnectionFactory.getConnection();
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
                if(rs.getObject(colField.getColumnName()).getClass().getSimpleName().equals("BigDecimal")){
                    Arrays.stream(methods)
                            .filter(setter -> setter.getName().equalsIgnoreCase("set"+colField.getFieldName()))
                            .findFirst().orElseThrow(RuntimeException::new)
                            .invoke(o, rs.getDouble(colField.getColumnName()));
                } else {
                    Arrays.stream(methods)
                            .filter(setter -> setter.getName().equalsIgnoreCase("set" + colField.getFieldName()))
                            .findFirst().orElseThrow(RuntimeException::new)
                            .invoke(o, rs.getObject(colField.getColumnName()));
                }
            }
        }
        connection.close();
        return o;
    }
}
