package com.scm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.services.ContactService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ContactService contactService;

    // user Dashboard page
    @GetMapping("/dashboard")
    public String getDashboard(Model model, @ModelAttribute("loggedInUser") User loggedInUser) {

        System.out.println("User Dashboard");
        // Fetch the recent contacts (e.g., created in the last 30 days)
        List<Contact> recentContacts = contactService.getRecentContacts(loggedInUser);

        // Fetch the total number of contacts
        long totalContacts = contactService.getTotalContactsCount(loggedInUser);

        // Fetch favorite contacts
        long favoriteContacts = contactService.getFavoriteContacts(loggedInUser);

        // Add the data to the model
        model.addAttribute("recentContacts", recentContacts);
        model.addAttribute("favoriteContacts", favoriteContacts);
        model.addAttribute("totalContacts", totalContacts);

        return "user/dashboard";
    }

    // user profile page
    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication) {

        System.out.println("User Profile");

        return "user/profile";
    }

}
