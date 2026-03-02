package com.appsdevelopersblog.app.ws.ui.model.request;

//This is a simple request model for password reset and only contains a field for email.
public class PasswordResetRequestModel {

    private String email;


    public void setEmail(String email){

        this.email = email;
    }

    public String getEmail(){

        return email;
    }
}
