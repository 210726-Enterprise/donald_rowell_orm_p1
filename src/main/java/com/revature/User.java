package com.revature;

import com.revature.annotations.*;

@Table(tableName = "users")
public class User {

    @PrimaryKey(columnName = "id")
    private int id;

    @Column(columnName = "user_name")
    private String userName;

    @Column(columnName = "password")
    private String password;

    @Column(columnName = "email")
    private String email;

    public User() {
        id = 0;
        userName = "Default";
        password = "password";
        email = "test@test.com";
    }

    public User(String userName, String password) {
        id = 0;
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
