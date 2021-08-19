package com.revature;

import java.lang.reflect.InvocationTargetException;

public class Driver {

    public static void main(String[] args) throws Exception {
        String url = "db";
        String userName = "postgres";
        String password = "password";
        User user = new User();
        ORM orm = new ORM(url, userName, password);

        // insert user into table
        orm.insert(user);

        // select user given a valid criteria
        user = (User) orm.setModel(user.getClass()).select().where("email","test@test.com");

        // update user value
        orm.update(user);

        // delete user from table
        orm.delete(user);
    }
}
