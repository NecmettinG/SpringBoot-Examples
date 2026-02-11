package com.appsdevelopersblog.app.ws.ui.controller;


import com.appsdevelopersblog.app.ws.exceptions.UserServiceException;
import com.appsdevelopersblog.app.ws.service.AddressService;
import com.appsdevelopersblog.app.ws.service.UserService;
import com.appsdevelopersblog.app.ws.service.impl.AddressServiceImpl;
import com.appsdevelopersblog.app.ws.service.impl.UserServiceImpl;
import com.appsdevelopersblog.app.ws.shared.dto.AddressDto;
import com.appsdevelopersblog.app.ws.shared.dto.UserDto;
import com.appsdevelopersblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdevelopersblog.app.ws.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//We have to use context path to make our tomcat server distinguish different applications to avoid conflict. Two different applications-
//-cannot use same root url which is http::localhost:8080. We have to change their root url like http::localhost:8080/mobile-app-ws or
//- http://localhost:8080/car-store etc. Context path will be implemented in properties file.
@RestController
@RequestMapping("/users") // http://localhost:8080/users, with context path: http://localhost:8080/mobile-app-ws/users
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    AddressServiceImpl addressesService;

    @Autowired
    AddressServiceImpl addressService;

    //We are going to add xml support for response. Default response format is Json but some client applications may want xml response.
    //This response type is defined in Http header called "Accept". It may take values like "applicaton/json", "application/xml" and so on.
    //Postman has wildcards for response types. If we type "*/*" in Accept header, Postman will accept all types of responses.
    //With "produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}", We added both Json and Xml response support.
    //But Json is prioritized because it is written as first. If client app. doesn't define "Accept" header, Default response will be Json.
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest getUser(@PathVariable("id") String id) {

        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);

        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    //We are going to return a list of users. page and limit comes from query string(http://localhost:8080/mobile-app-ws/users?page=0&limit=50).
    //query string starts from "?".
    //value parameter takes the value of the specific key that is in query string. We can also use default value if we don't pass any value.
    //This get request only produces output so we added both json and xml support. Get request won't accept any information in Http body, so We-
    //-did not use "consumes". PAGE STARTS FROM 0 IN SQL BTW. REMEMBER THAT! 0 IS THE FIRST PAGE!
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {

        List<UserRest> returnValue = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page, limit);

        //Enhanced for loop for converting each UserDto object to UserRest object and store them into returnValue array list.
        for (UserDto userDto : users) {
            //We are going to create a UserRest object to copy properties from UserDto object to this UserRest object.
            UserRest userModel = new UserRest();

            BeanUtils.copyProperties(userDto, userModel);

            //We added the UserRest object into returnValue Array List.
            returnValue.add(userModel);
        }

        return returnValue;
    }

    //Our Post request method can accept User Details as Xml format currently. We implemented Xml support in both request and response.
    //"consumes" is responsible for request body. We define the format of the request input with the Http header called "Content-Type".
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws UserServiceException {

        UserRest returnValue = new UserRest();//This instance is for response.

        //If firstname is not entered in request body, we will throw our custom exception and error message that is from ErrorMessages enum.
        if (userDetails.getFirstName().isEmpty()) {

            //We pass the error message, comes from ErrorMessages enum, to UserServiceException.
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        //UserDto userDto = new UserDto();
        //BeanUtils is good for copying data to source object to destination object. But it is problematic when source object has an object as-
        //-attribute. We added AddressRequestModel list to our UserDetailsRequestModel class as an attribute. We are gonna use ModelMapper.
        //BeanUtils.copyProperties(userDetails, userDto); //We copied userDetails properties' values and pasted to userDto instance.

        ModelMapper modelMapper = new ModelMapper();//Instantiate a ModelMapper instance first.

        //.map(source object, destination class) method returns an instance that is an object of destination class.
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);//Destination parameter is a bit different from BeanUtils, takes datatype.

        UserDto createdUser = userService.createUser(userDto);//createdUser is the returned instance from userService.

        //BeanUtils.copyProperties(createdUser, returnValue);//createdUser properties' values are copied and are pasted into returnedValue.

        returnValue = modelMapper.map(createdUser, UserRest.class);
        return returnValue;
    }

    @PutMapping(
            path = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public UserRest updateUser(@RequestBody UserDetailsRequestModel userDetails, @PathVariable("id") String id) {

        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userDetails, userDto);

        UserDto updatedUser = userService.updateUser(id, userDto);

        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    //We won't return user details in this function because we are deleting it. A return message like "Success" is enough. That's why We-
    //-created OperationStatusModel class to perform this task.
    //Whenever we delete a user that is logged in, We are still able to delete other users due to deleted user's json web token, which is still-
    //-valid. We have to fix this. This is how you can do it:
    //1. When JWT token is initially generated, add the value of userId in to your token.
    //2. When a request for DELETE userId comes in, read JWT from the HTTP Header value and extract userId from Token.
    //3. Compare userId which was read from JWT Token with a userId read from Request Path variable. If they do not match, fail the request.
    //I am going to configure this later.
    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable("id") String id) {

        OperationStatusModel returnValue = new OperationStatusModel();

        //.name() method is used for enums to convert the enum name to string.
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    // http://localhost:8080/mobile-app-ws/users/{id}/addresses
    //For using HATEOAS on a method that returns list of AddressesRest, we will use CollectionModel instead of EntityModel.
    //The json response of CollectionModel is a bit different from EntityModel. Our addresses are values of a key called "_embedded".
    @GetMapping(path = "/{id}/addresses", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public CollectionModel<AddressesRest> getUserAddresses(@PathVariable("id") String id) {

        List<AddressesRest> returnValue = new ArrayList<>();

        List<AddressDto> addressesDto = addressesService.getAddresses(id);

        if (addressesDto != null && !addressesDto.isEmpty()) {

            //We can also copy and map an entire list with using ModelMapper but destination data type is a bit different.
            Type listType = new TypeToken<List<AddressesRest>>() {
            }.getType();
            ModelMapper modelMapper = new ModelMapper();
            returnValue = modelMapper.map(addressesDto, listType);

            //We implemented this for loop for adding links to each address element in the list.
            for (AddressesRest addressRest : returnValue) {

                // http://localhost:8080/users/<userId>/addresses/<addressId>
                Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                                .getUserAddress(id, addressRest.getAddressId()))
                        .withSelfRel();

                addressRest.add(selfLink);
            }
        }

        // http://localhost:8080/users/<userId> This link is for certain user with certain userId. Link class comes from HATEOAS.
        Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).withRel("user");

        // http://localhost:8080/users/<userId>/addresses This is a self link. Be careful that we used .withSelfRel() instead of-
        //- .withRel("<relation name>"). The method that is from UserController takes path variable parameter.(.getUserAddresses(id))
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddresses(id)).withSelfRel();

        return CollectionModel.of(returnValue, userLink, selfLink);

        //Example Json output:
        //{
        //    "_embedded": {
        //        "addressesRestList": [
        //            {
        //                "addressId": "Q8Uy81e1a4FtiwnvUhhwDhZVd2SKCt",
        //                "city": "Istanbul",
        //                "country": "Turkey",
        //                "streetName": "Aydinlar Streeet",
        //                "postalCode": "34569",
        //                "type": "BILLING",
        //                "_links": {
        //                    "self": {
        //                        "href": "http://localhost:8080/mobile-app-ws/users/yGCi6nNJ4Crjhu914GnjhpDBaxEjG9/addresses/Q8Uy81e1a4FtiwnvUhhwDhZVd2SKCt"
        //                    }
        //                }
        //            },
        //            {
        //                "addressId": "uXCwSChzhLIXcLIlvOclJs0pI4PHRn",
        //                "city": "Istanbul",
        //                "country": "Turkey",
        //                "streetName": "Aydinlar Streeet",
        //                "postalCode": "34569",
        //                "type": "SHIPPING",
        //                "_links": {
        //                    "self": {
        //                        "href": "http://localhost:8080/mobile-app-ws/users/yGCi6nNJ4Crjhu914GnjhpDBaxEjG9/addresses/uXCwSChzhLIXcLIlvOclJs0pI4PHRn"
        //                    }
        //                }
        //            }
        //        ]
        //    },
        //    "_links": {
        //        "user": {
        //            "href": "http://localhost:8080/mobile-app-ws/users/yGCi6nNJ4Crjhu914GnjhpDBaxEjG9"
        //        },
        //        "self": {
        //            "href": "http://localhost:8080/mobile-app-ws/users/yGCi6nNJ4Crjhu914GnjhpDBaxEjG9/addresses"
        //        }
        //    }
        //}
    }

    //We will use HATEOAS on this api endpoint. We extended our AddressesRest class with RepresentationModel.
    //HATEOAS is simply we return multiple endpoint addresses, that is relevant to this endpoint, in response.
    //Example json output is written in AddressesRest class. Check there.
    @GetMapping(path = "/{userId}/addresses/{addressId}", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public EntityModel<AddressesRest> getUserAddress(@PathVariable("userId") String userId, @PathVariable("addressId") String addressId) {

        AddressDto addressDto = addressService.getAddress(addressId);

        ModelMapper modelMapper = new ModelMapper();
        AddressesRest returnValue = modelMapper.map(addressDto, AddressesRest.class);

        // http://localhost:8080/users/<userId> This link is for certain user with certain userId. Link class comes from HATEOAS.
        Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userId).withRel("user");

        // http://localhost:8080/users/<userId>/addresses This link is for all user addresses. We can also get rid of .slash() methods.
        //Notice that the link we built is the same endpoint with getUserAddresses. We can use methodOn() to avoid hardcoding which is-
        //- the usage of .slash(). I will leave userLink as it is because I want to demonstrate both the usage of .slash() and .methodOn().
        Link userAddressesLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddresses(userId))
                //.slash(userId)
                //.slash("addresses")
                .withRel("addresses");

        // http://localhost:8080/users/<userId>/addresses/<addressId> This is a self link. Be careful that we used .withSelfRel() instead of-
        //- .withRel("<relation name>").
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddress(userId, addressId))
                //.slash(userId)
                //.slash("addresses")
                //.slash(addressId)
                .withSelfRel();

        //RepresentationModel class includes a method called .add() for adding link to response class.
        /*
        returnValue.add(userLink);
        returnValue.add(userAddressesLink);
        returnValue.add(selfLink);
         */

        //We can also use EntityModel to wrap our single object that we are returning and it will allow us to add links to it as well.
        //No need to use .add() method or no need to extend the AddressesRest class with RepresentationModel.
        //I will add them to comment line just in case. We are going to return EntityModel in this method.
        return EntityModel.of(returnValue, Arrays.asList(userLink, userAddressesLink, selfLink));
    }

}
