
package com.lmts.service;

import com.lmts.Dao.CategoryDao;
import com.lmts.model.Category;
import java.util.List;

public class CategoryService {
    private CategoryDao categoryDao;
    
    public CategoryService(){
        this.categoryDao = new CategoryDao();
    }
    
    public List<Category> getAllCategory(){
        return this.categoryDao.getAllCategory();
    }
    
}
