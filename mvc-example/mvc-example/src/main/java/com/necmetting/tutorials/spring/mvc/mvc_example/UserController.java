package com.necmetting.tutorials.spring.mvc.mvc_example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    //You can inspect @RequestParam's parameter here.
    @GetMapping(path ="/users")
    public ModelAndView getUsers(@RequestParam(name = "limit", defaultValue = "30"/*,required = false*/) int limit){

        ModelAndView modelAndView = new ModelAndView("users");

        return modelAndView;
    }
}
