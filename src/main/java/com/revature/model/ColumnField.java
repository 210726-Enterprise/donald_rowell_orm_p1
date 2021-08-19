package com.revature.model;

import com.revature.annotations.Column;

import java.lang.reflect.Field;

public class ColumnField {

    private Field field;

    public ColumnField(Field field){
        this.field = field;
    }

    public Field getField(){
        return field;
    }

    public String getFieldName(){
        return field.getName();
    }

    public Class<?> getFieldType(){
        return field.getType();
    }

    public String getColumnName(){
        return field.getAnnotation(Column.class).columnName();
    }
}
