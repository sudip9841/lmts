
package com.lmts.Dao;

import com.lmts.helpers.DBUtils;
import com.lmts.model.Category;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class CategoryDao {
    
    private Connection con;
    
    public CategoryDao(){
        try{
            this.con = DBUtils.getDBConnection();
        }catch(SQLException throwables){
           throwables.printStackTrace();
        }
    }
    
    public List<Category> getAllCategory() {
        List<Category> categoryList = new ArrayList<>();

        try {
            String selectQuery = "SELECT * FROM ticket_type";
            try (Statement statement = con.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    Category category = new Category();
                    category.setCategoryId(resultSet.getInt("id"));
                    category.settCategoryName(resultSet.getString("type_name"));
                    category.setPrice(resultSet.getInt("price"));
                    categoryList.add(category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }

        return categoryList;
    }
    
    
    
}
