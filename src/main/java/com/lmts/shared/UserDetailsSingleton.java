
package com.lmts.shared;

public class UserDetailsSingleton {
    private static UserDetailsSingleton instance;

    private int userId;
    private String userName;
    private String email;
    private int role;

    private UserDetailsSingleton() {
        // Private constructor to prevent instantiation
    }

    public static UserDetailsSingleton getInstance() {
        if (instance == null) {
            instance = new UserDetailsSingleton();
        }
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole(){
        return this.role;
    }
    
    public void setRole(int role){
        this.role = role;
    }
}
