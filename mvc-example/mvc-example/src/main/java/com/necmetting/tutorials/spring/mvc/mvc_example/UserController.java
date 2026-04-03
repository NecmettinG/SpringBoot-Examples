package com.necmetting.tutorials.spring.mvc.mvc_example;

import com.necmetting.tutorials.spring.mvc.mvc_example.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

//You already know the basics of @PathVariable and @RequestParam annotations.
@Controller
public class UserController {

    @GetMapping(path ="/users/{userId}/albums/{albumId}")
    public ModelAndView getAlbum(@PathVariable("userId") String userId,
                                 @PathVariable("albumId") String albumId){

        ModelAndView modelAndView = new ModelAndView("album");

        modelAndView.addObject("userId", userId);
        modelAndView.addObject("albumId", albumId);

        return modelAndView;
    }

    //You can inspect @RequestParam's parameters here.
    @GetMapping(path ="/users")
    public ModelAndView getUsers(@RequestParam(name = "limit", defaultValue = "30"/*,required = false*/) int limit){

        ModelAndView modelAndView = new ModelAndView("users");

        return modelAndView;
    }

    //We are going to read form data in this function.
    // LECTURE NOTES:
    // When the form is submitted via POST to "/users", this method is triggered.
    // The @ModelAttribute annotation automatically binds the incoming form fields to a new User object.
    // Spring does this by matching the HTTP request parameters (the 'name' attributes from the HTML form)
    // to the setter methods of the User class. It then adds this populated User object to the Model 
    // so it can be accessed in the resulting view.
    @PostMapping(path = "/users")
    public String signupFormSubmit(@ModelAttribute User user){

        // Return the name of the Thymeleaf template ("signup-result.html") to display the submitted data.
        return "signup-result";
    }

    // LECTURE NOTES:
    // This simple GET mapping is just responsible for showing the initial signup form.
    // Navigating to http://localhost:8080/signup in the browser will render "signup.html".
    @GetMapping(path="/signup")
    public String signupForm(){

        return "signup";
    }
}
