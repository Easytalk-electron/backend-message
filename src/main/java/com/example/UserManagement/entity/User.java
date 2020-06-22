package com.example.UserManagement.entity;

public class User {
    private int id;
    private String username;
    private String password;
    private String introduction;
    private String headPic;


    //... getter and setter

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
}