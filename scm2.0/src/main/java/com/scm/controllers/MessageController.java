package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.EmailService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/user/messages")
public class MessageController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sendmail")
    public String sendMessage() {
         return "user/direct-message";
    }

    @PostMapping("/sendmail")
    public String sendMail(@RequestParam("contactEmail") String contactEmail,
                           @RequestParam("subject") String subject,
                           @RequestParam("message") String message,HttpSession session) {

        emailService.sendEmail(contactEmail, subject, message);

        session.setAttribute("message", Message.builder()
                        .type(MessageType.green)
                        .content("Message sent successfully.")
                        .build());

        return "redirect:/user/messages/sendmail";
    }
}
