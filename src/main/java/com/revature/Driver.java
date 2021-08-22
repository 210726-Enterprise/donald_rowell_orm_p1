package com.revature;

public class Driver {

    public static void main(String[] args) throws Exception {
        String url = System.getenv("db_url");
        String userName = System.getenv("db_username");
        String password = System.getenv("db_password");
        User user = new User("don", "pass", "test@test.com");
        ORM ormUser = new ORM(User.class);

        System.out.println(url);
        System.out.println(userName);
        System.out.println(password);
        System.out.println(user);
        // insert user into table
        user.setId(ormUser.insert(user));

        System.out.println(user);
        // select user given a valid criteria
        User newUser = (User) ormUser.select(user.getClass()).where("user_name","Test");

        System.out.println(user);
        System.out.println(newUser);
        // update user value
        boolean updateTest = ormUser.update(user);

        System.out.println(updateTest);
        System.out.println(user);
        System.out.println(newUser);
        // delete user from table
        boolean deleteTest = ormUser.delete(user);

        System.out.println(deleteTest);
    }
}
