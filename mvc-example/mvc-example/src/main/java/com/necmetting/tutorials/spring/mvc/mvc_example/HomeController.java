package com.necmetting.tutorials.spring.mvc.mvc_example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Handles GET requests to the root URL ("/").
     * The @GetMapping annotation maps the HTTP GET request to this method.
     * Returning "home" tells Spring MVC to look for a view template named "home" (e.g., home.jsp).
     */
    @GetMapping("/")
    public String homePage(){

        return "home";
    }
}
