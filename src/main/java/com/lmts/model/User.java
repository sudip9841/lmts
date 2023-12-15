
package com.lmts.model;

public class User {
    private Integer id;
    private String userName;
    private String email;
    private String password;
    
    public User(){
        
    }
    
    
    //setter methods
    public void setId(Integer id){
        this.id = id;
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    //getter methods
    
    public Integer getId(){
        return this.id;
    }
    
    public String getUserName(){
        return this.userName;
    }
    
    public String getEmail(){
        return this.email;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    
    
}
