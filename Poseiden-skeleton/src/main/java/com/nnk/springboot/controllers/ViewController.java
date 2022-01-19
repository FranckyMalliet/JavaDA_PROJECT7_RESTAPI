package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping(value="/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping(value="/home")
    public String homePage(){
        return "home";
    }
}
