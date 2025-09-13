package com.appsdevelopersblog.app.ws.ui.model.response;

//This class is for generating response whenever we create a new user. We won't return sensitive information like password or id from database.
public class UserRest {

    private String userId; //This user id is not the id from database.
    // It will be generated randomly, and We are going to search particular user with that id. This adds protection to our data.
    private String firstName;
    private String lastName;
    private String email;

    public String getUserId(){

        return userId;
    }

    public void setUserId(String userId){

        this.userId = userId;
    }

    public String getFirstName(){

        return firstName;
    }

    public void setFirstName(String firstName){

        this.firstName = firstName;
    }

    public String getLastName(){

        return lastName;
    }

    public void setLastName(String lastName){

        this.lastName = lastName;
    }

    public String getEmail(){

        return email;
    }

    public void setEmail(String email){

        this.email = email;
    }
}
