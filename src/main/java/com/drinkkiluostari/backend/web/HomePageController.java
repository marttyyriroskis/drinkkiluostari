package com.drinkkiluostari.backend.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @GetMapping(value = { "*" })
    public String showHomePage() {
        return "/index";
    }    

}
