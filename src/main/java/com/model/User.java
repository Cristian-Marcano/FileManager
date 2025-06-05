package com.model;

/**
 *
 * @author Cristian
 */
public class User {
    
    private int id;
    private String username, password, position;
    private boolean access;
    
    public User(int id, String username, String password, String position, boolean access) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.position = position;
        this.access = access;
    }
    
    //* Setters
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public void setAccess(boolean access) {
        this.access = access;
    }

    //* Getters
    public int getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getPosition() {
        return position;
    }
    
    public boolean getAccess() {
        return access;
    }
}
