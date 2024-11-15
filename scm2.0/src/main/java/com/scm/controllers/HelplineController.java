package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.ContactHomeMessage;
import com.scm.entities.ContactMessage;
import com.scm.repositories.ContactHomeMessageRepo;
import com.scm.repositories.ContactMessageRepo;
import com.scm.services.EmailService;

@Controller
@RequestMapping("/contact-admin")
public class HelplineController {

    @Autowired
    private ContactMessageRepo contactMessageRepo;

    @Autowired
    private ContactHomeMessageRepo contactHomeMessageRepo;

    @PostMapping("/contact-page-message")
    public String handleContactPageMessage(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            Model model) {

        // Save the message to the database
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setName(name);
        contactMessage.setEmail(email);
        contactMessage.setSubject(subject);
        contactMessage.setMessage(message);

        contactMessageRepo.save(contactMessage);

        // Add attributes for confirmation view
        model.addAttribute("confirmationContactMessage", "Thank you for reaching out! We'll get back to you soon.");

        return "contact-confirmation";
    }

    @PostMapping("/contact-home-message")
    public String handleHomePageMessage(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("message") String message,
            Model model) {

        // Save the message to the database
        ContactHomeMessage contactHomeMessage = new ContactHomeMessage();
        contactHomeMessage.setName(name);
        contactHomeMessage.setEmail(email);
        contactHomeMessage.setMessage(message);

        contactHomeMessageRepo.save(contactHomeMessage);

        // Add attributes for confirmation view
        model.addAttribute("confirmationHomeMessage", "Thank you for reaching out! We'll get back to you soon.");

        return "home-confirmation";
    }



}
