package com.necmetting.tutorials.spring.mvc.mvc_example.model;

public class User {

    // LECTURE NOTES:
    // This is our Model class (or POJO - Plain Old Java Object).
    // IMPORTANT: The variable names here (like 'firstName', 'lastName') MUST exactly match 
    // the 'name' attributes of the <input> fields in your HTML form (e.g., name="firstName").
    // Under the hood, Spring uses the setter methods (setFirstName, etc.) to inject the form data into this object.
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String repeatPassword;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
