package com.necmetting.tutorials.spring.mvc.mvc_example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Handles GET requests to the root URL ("/").
     * The @GetMapping annotation maps the HTTP GET request to this method.
     * 
     * Returning "home" tells Spring MVC to look for a view template named "home".
     * - If you are using Thymeleaf (the default modern approach), Spring looks for 'home.html' inside the 'src/main/resources/templates/' directory.
     * - If you are using JSP (the legacy approach), Spring typically looks for 'home.jsp' configured inside the '/WEB-INF/jsp/' directory.
     */
    @GetMapping("/")
    public String homePage(){

        return "home";
    }
}
