package com.revature;

import com.revature.DDL.DDL;
import com.revature.DML.*;
import com.revature.model.BasicModel;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ORM<T> {

    private DDL ddl;
    protected BasicModel<T> model;
    protected static String[] db;

    public ORM(){}
/*
    public ORM(Class<T> clazz){
        ddl = new DDL();
        model = new BasicModel<>(clazz);
    }
*/
    public ORM(String URL, String userName, String password){
        this.db[0] = URL;
        this.db[1] = userName;
        this.db[2] = password;
    }

    public DDL DDL() {
        return ddl;
    }

    public void setDdl(DDL ddl) {
        this.ddl = ddl;
    }


    public int insert(T obj) {
        setModel((Class<T>) obj.getClass());
        Insert insert = new Insert(obj, model);
        return insert.insert();
    }

    public Select select(Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        setModel(clazz);
        Select<T> select = new Select(model);
        return select;
    }

    public boolean update(T obj) {
        setModel((Class<T>) obj.getClass());
        Update update = new Update(obj, model);
        return update.update();
    }

    public boolean delete(T obj) throws InvocationTargetException, IllegalAccessException {
        setModel((Class<T>) obj.getClass());
        int id = (Integer) Arrays.stream(obj.getClass().getDeclaredMethods())
                            .filter(m -> m.getName().equalsIgnoreCase("get" + model.getPrimaryKey().getFieldName()))
                            .findFirst().orElseThrow(RuntimeException::new)
                            .invoke(obj);
        return Delete.delete(id, model.getTableName(), model.getPrimaryKey().getColumnName());
    }

    private void setModel(Class<T> clazz){
        this.model = new BasicModel<>(clazz);
    }
}
