package com.example.teknofest_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebsiteController {

    @GetMapping("/index")
    public String welcomePage(Model model) {
        return "index"; // Returns welcome.html from templates folder

    }
}
