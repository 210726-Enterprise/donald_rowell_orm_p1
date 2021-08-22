package com.revature;

import com.revature.DML.*;
import com.revature.model.BasicModel;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ORM<T> {

    private BasicModel<T> model;

    public ORM(Class<T> clazz){
        this.model = new BasicModel<>(clazz);
    }

    public int insert(T obj) {
        Insert<T> insert = new Insert(obj, model);
        return insert.insert();
    }

    public Select select(Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Select<T> select = new Select(model);
        return select;
    }

    public boolean update(T obj) {
        Update<T> update = new Update(obj, model);
        return update.update();
    }

    public boolean delete(T obj) throws InvocationTargetException, IllegalAccessException {
        int id = (Integer) Arrays.stream(obj.getClass().getDeclaredMethods())
                            .filter(m -> m.getName().equalsIgnoreCase("get" + model.getPrimaryKey().getFieldName()))
                            .findFirst().orElseThrow(RuntimeException::new)
                            .invoke(obj);
        return Delete.delete(id, model.getTableName(), model.getPrimaryKey().getColumnName());
    }
}
