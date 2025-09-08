package com.appsdevelopersblog.app.ws.ui.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users") // http::localhost:8080/users
public class UserController{


    @GetMapping
    public String getUser(){

        return "get user is called.";
    }

    @PostMapping
    public String createUser(){

        return "create user is called.";
    }

    @PutMapping
    public String updateUser(){

        return "update user is called.";
    }

    @DeleteMapping
    public String deleteUser(){

        return "delete user is called.";
    }
}
