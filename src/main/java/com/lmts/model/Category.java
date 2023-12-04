
package com.lmts.model;


public class Category {
    private int categoryId;
    private String categoryName;
    private float price;
    
    public Category(){
    }
    
    public int getCategoryId(){
        return this.categoryId;
    }
    
    public String getCategoryName(){
        return this.categoryName;
    }
    
    public float getPrice(){
        return this.price;
    }
    
    public void setCategoryId(int categoryId){
        this.categoryId = categoryId;
    }
    
    public void settCategoryName(String categoryName){
        this.categoryName = categoryName;
    }
    
    public void setPrice(float price){
        this.price = price;
    }
    
}
