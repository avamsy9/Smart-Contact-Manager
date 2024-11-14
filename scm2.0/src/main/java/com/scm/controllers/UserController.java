package com.scm.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/user")
public class UserController {

    
    //user Dashboard page
    @GetMapping("/dashboard")
    public String userDashboard() {
        System.out.println("User Dashboard");
        return "user/dashboard";
    }

    
    //user profile page
    @GetMapping("/profile")
    public String userProfile() {

        System.out.println("User Profile");

        return "user/profile";
    }

   
    
    //user add contacts page

    //user view contacts

    //user edit contact

    //user delete contact

    //user search contact

}
