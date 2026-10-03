package com.example.th2soa.dto;

public class LoginRequest {

    private String userName;
    private String password; // da ma hoa Base64 tai client

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}