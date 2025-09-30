package com.appsdevelopersblog.app.ws.ui.controller;


import com.appsdevelopersblog.app.ws.service.UserService;
import com.appsdevelopersblog.app.ws.service.impl.UserServiceImpl;
import com.appsdevelopersblog.app.ws.shared.dto.UserDto;
import com.appsdevelopersblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdevelopersblog.app.ws.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users") // http::localhost:8080/users
public class UserController{

    @Autowired
    UserServiceImpl userService;

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable("id") String id){

        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);

        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){

        UserRest returnValue = new UserRest();//This instance is for response.

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userDetails, userDto); //We copied userDetails properties' values and pasted to userDto instance.

        UserDto createdUser = userService.createUser(userDto);//createdUser is the returned instance from userService.

        BeanUtils.copyProperties(createdUser, returnValue);//createdUser properties' values are copied and are pasted into returnedValue.

        return returnValue;
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
