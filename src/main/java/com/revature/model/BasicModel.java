package com.revature.model;

import com.revature.annotations.*;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class BasicModel<T> {

    private Class<T> clazz;
    private PrimaryKeyField primaryKey;
    private List<ColumnField> columnFields;

    public static <T> BasicModel<T> of(Class<T> clazz) {
        if (clazz.getAnnotation(Table.class) == null) {
            throw new IllegalStateException("Cannot create BasicModel object! Provided class, " + clazz.getName() + " is not annotated with @Table");
        }
        return new BasicModel<>(clazz);
    }

    public BasicModel(Class<T> clazz){
        this.clazz = clazz;
        this.columnFields = new LinkedList<>();
        setPrimaryKeyField();
        setColumnFields();
    }

    public String getClassName(){
        return clazz.getName();
    }

    public String getSimpleClassName(){
        return clazz.getSimpleName();
    }

    public PrimaryKeyField getPrimaryKey(){
        return primaryKey;
    }

    public List<ColumnField> getColumnFields(){
        return columnFields;
    }

    private void setPrimaryKeyField(){
        Field[] fields = clazz.getDeclaredFields();
        boolean set = true;
        for(Field field:fields){
            PrimaryKey pk = field.getAnnotation(PrimaryKey.class);
            if(pk != null){
                primaryKey = new PrimaryKeyField(field);
                set = false;
            }
        }
        if(set) throw new RuntimeException("No field annotated with @PrimaryKey in: " + getClassName());
    }

    private void setColumnFields(){
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            Column col = field.getAnnotation(Column.class);
            if(col != null){
                columnFields.add(new ColumnField(field));
            }
        }
        if(columnFields.isEmpty()) throw new RuntimeException("No field annotated with @Column in: " + getClassName());
    }

}
