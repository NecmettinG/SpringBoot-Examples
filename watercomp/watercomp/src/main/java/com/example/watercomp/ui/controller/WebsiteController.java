package com.example.watercomp.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebsiteController {

    @GetMapping("/map")
    public String welcomePage(Model model) {
        return "map"; // Returns map.html from templates folder

    }
}
