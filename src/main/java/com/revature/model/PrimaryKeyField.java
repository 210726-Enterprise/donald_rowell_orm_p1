package com.revature.model;

import com.revature.annotations.PrimaryKey;

import java.lang.reflect.Field;

public class PrimaryKeyField {

    private Field field;

    public PrimaryKeyField(Field field){
        this.field = field;
    }

    public String getFieldName(){
        return field.getName();
    }

    public Class<?> getFieldType(){
        return field.getType();
    }

    public String getColumnName(){
        return field.getAnnotation(PrimaryKey.class).columnName();
    }
}
