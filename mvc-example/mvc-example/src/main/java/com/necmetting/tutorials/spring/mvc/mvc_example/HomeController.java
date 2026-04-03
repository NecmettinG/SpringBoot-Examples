package com.necmetting.tutorials.spring.mvc.mvc_example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
    public String homePage(Model model){

        // The Model object acts as a container to pass data from the controller to the view (Thymeleaf/JSP).
        // addAttribute(key, value) binds data to a specific name.
        // The key (e.g., "firstName") is what we will use in our template to access the value (e.g., "Necmettin").
        model.addAttribute("firstName", "Necmettin");
        model.addAttribute("lastName", "Gedikli");
        model.addAttribute("modelType", "Model");

        return "home";
    }

    @GetMapping("/model-map-example")
    public String homePage(ModelMap model){

        // ModelMap is very similar to Model. While Model is an interface, ModelMap is a class implementing Map.
        // It provides the same functionality of passing data to the view, but gives you more Map-like methods if needed.
        // In most standard Spring MVC use-cases, Model and ModelMap can be used interchangeably.
        model.addAttribute("firstName", "Necmettin");
        model.addAttribute("lastName", "Gedikli");
        model.addAttribute("modelType", "ModelMap");

        return "home";
    }
}
