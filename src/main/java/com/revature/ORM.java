package com.revature;

import com.revature.DDL.DDL;
import com.revature.DML.*;
import com.revature.model.BasicModel;

import java.lang.reflect.InvocationTargetException;

public class ORM<T> {

    private DDL ddl;
    protected BasicModel<T> model;
    protected static final String[] db = new String[3];

    public ORM(){}
/*
    public ORM(Class<T> clazz){
        ddl = new DDL();
        model = new BasicModel<>(clazz);
    }
*/
    public ORM(String URL, String userName, String password){
        db[0] = URL;
        db[1] = userName;
        db[2] = password;
    }

    public ORM setModel(Class<T> clazz){
        model = new BasicModel<>(clazz);
        return this;
    }

    public DDL DDL() {
        return ddl;
    }

    public void setDdl(DDL ddl) {
        this.ddl = ddl;
    }


    public int insert(T obj) {
        Insert insert = new Insert(obj, model);
        return insert.insert();
    }

    public Select select() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Select<T> select = new Select(model);
        return select;
    }

    public Update update() {
        Update update = new Update();
        return update;
    }

    public Delete delete() {
        Delete delete = new Delete();
        return delete;
    }
}
