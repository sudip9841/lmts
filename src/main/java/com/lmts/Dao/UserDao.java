
package com.lmts.Dao;

import com.lmts.helpers.DBUtils;
import com.lmts.model.User;
import com.lmts.shared.AlertMessageDialogBox;
import com.lmts.shared.UserDetailsSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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
    try {
        // No need to declare a separate connection; use the class-level connection
        String query = "SELECT * FROM user WHERE user_name = ? AND password = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("id");
                    String userName = resultSet.getString("user_name");
                    String userEmail = resultSet.getString("email");
                    int role = resultSet.getInt("role_type");

                    // Save user details in UserDetailsSingleton
                    UserDetailsSingleton userDetails = UserDetailsSingleton.getInstance();
                    userDetails.setUserId(userId);
                    userDetails.setUsername(userName);
                    userDetails.setEmail(userEmail);
                    userDetails.setRole(role);

                    return true;
                } else {
                    return false;
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception and display an error message
        return false;
    }
}

    
}
