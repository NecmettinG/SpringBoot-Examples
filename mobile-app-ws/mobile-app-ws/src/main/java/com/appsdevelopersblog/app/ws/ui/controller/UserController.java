package com.appsdevelopersblog.app.ws.ui.controller;


import com.appsdevelopersblog.app.ws.exceptions.UserServiceException;
import com.appsdevelopersblog.app.ws.service.UserService;
import com.appsdevelopersblog.app.ws.service.impl.UserServiceImpl;
import com.appsdevelopersblog.app.ws.shared.dto.UserDto;
import com.appsdevelopersblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdevelopersblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdevelopersblog.app.ws.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users") // http::localhost:8080/users
public class UserController{

    @Autowired
    UserServiceImpl userService;

    //We are going to add xml support for response. Default response format is Json but some client applications may want xml response.
    //This response type is defined in Http header called "Accept". It may take values like "applicaton/json", "application/xml" and so on.
    //Postman has wildcards for response types. If we type "*/*" in Accept header, Postman will accept all types of responses.
    //With "produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}", We added both Json and Xml response support.
    //But Json is prioritized because it is written as first. If client app. doesn't define "Accept" header, Default response will be Json.
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest getUser(@PathVariable("id") String id){

        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);

        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    //Our Post request method can accept User Details as Xml format currently. We implemented Xml support in both request and response.
    //"consumes" is responsible for request body. We define the format of the request input with the Http header called "Content-Type".
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws UserServiceException{

        UserRest returnValue = new UserRest();//This instance is for response.

        //If firstname is not entered in request body, we will throw our custom exception and error message is from ErrorMessages enum.
        if(userDetails.getFirstName().isEmpty()){

            //We pass the error message, comes from ErrorMessages enum, to UserServiceException.
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

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
