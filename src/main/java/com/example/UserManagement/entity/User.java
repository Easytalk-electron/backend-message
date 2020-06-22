package com.example.UserManagement.entity;

import org.apache.shiro.crypto.hash.Sha256Hash;

import javax.persistence.Column;

public class User {
    private int id;
    private String username;
    private String introduction;
    private String headPic;

    @Column(nullable = false, columnDefinition = "char(64)")
    private String password;


    //... getter and setter

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
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

    public void setPassword(String _password) {
        password = new Sha256Hash(_password).toHex();
    }

    public boolean checkPassword(String _password) {
        return password.equals(new Sha256Hash(_password).toHex());
    }

    public boolean checkPasswordHash(String _passwordHash) {
        return password.equals(_passwordHash);
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
}