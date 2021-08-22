package com.revature;

import com.revature.DML.*;
import com.revature.model.BasicModel;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ORM<T> {

    private BasicModel<T> model;
    private String[] db = new String[3];

    public ORM(){}

    public ORM(String URL, String userName, String password){
        this.db[0] = URL;
        this.db[1] = userName;
        this.db[2] = password;
    }

    public int insert(T obj) {
        setModel((Class<T>) obj.getClass());
        Insert<T> insert = new Insert(db, obj);
        return insert.insert(model);
    }

    public Select select(Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        setModel(clazz);
        Select<T> select = new Select(db, model);
        return select;
    }

    public boolean update(T obj) {
        setModel((Class<T>) obj.getClass());
        Update<T> update = new Update(obj, model);
        return update.update(db);
    }

    public boolean delete(T obj) throws InvocationTargetException, IllegalAccessException {
        setModel((Class<T>) obj.getClass());
        int id = (Integer) Arrays.stream(obj.getClass().getDeclaredMethods())
                            .filter(m -> m.getName().equalsIgnoreCase("get" + model.getPrimaryKey().getFieldName()))
                            .findFirst().orElseThrow(RuntimeException::new)
                            .invoke(obj);
        return Delete.delete(db, id, model.getTableName(), model.getPrimaryKey().getColumnName());
    }

    private void setModel(Class<T> clazz){
        this.model = new BasicModel<>(clazz);
    }
}
