/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lmts.Dao;

import com.lmts.helpers.DBUtils;
import com.lmts.model.User;
import com.lmts.service.UserService;
import com.lmts.shared.AlertMessageDialogBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sudip
 */
public class UserDao {
    private Connection con;
    
    
    public UserDao(){
        try{
            this.con = DBUtils.getDBConnection();
        }catch(SQLException throwables){
           throwables.printStackTrace();
        }
    }
    
    
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try {
            String selectQuery = "SELECT * FROM user";
            try (Statement statement = con.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUserName(resultSet.getString("user_name"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));

                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }

        return userList;
    }
    
    public boolean addUser(String userName, String email, String password){
        try{
            String insert = "INSERT INTO user (user_name, email, password)" +
                    "VALUES (?,?,?)";
            PreparedStatement statement = con.prepareStatement(insert);
            statement.setString(1, userName);
            statement.setString(2, email);
            statement.setString(3, password);
            
            int rowAffected = statement.executeUpdate();
            statement.close();
            
            return rowAffected>0;
            
        }catch(SQLException e){
            e.printStackTrace();
            AlertMessageDialogBox.showError(e.getMessage(), "Error");
            return false;
        }
    }
    
    public boolean isValidCredentials(String username, String password) {

       Connection connection = null;
        try {
            connection = con;

            // Query to check if the username and password match
            String query = "SELECT * FROM user WHERE user_name = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // If any row is returned, credentials are valid
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception and display an error message
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
