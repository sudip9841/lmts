/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmts.service;

import com.lmts.Dao.UserDao;
import com.lmts.model.User;
import java.util.List;


public class UserService {
     private UserDao userDao;

    // Constructor
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
    
    public void addtUser(String userName, String email, String password){
        this.userDao.addUser(userName,email,password);
    }
    
    public boolean isValidCredentials(String username, String password) {
       return this.userDao.isValidCredentials(username, password);
    }
}
