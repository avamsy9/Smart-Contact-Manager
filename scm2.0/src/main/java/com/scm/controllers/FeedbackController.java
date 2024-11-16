package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Feedback;
import com.scm.forms.FeedbackForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.FeedbackService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/feedback")
    public String sendFeedback(Model model) {

        FeedbackForm feedbackForm = new FeedbackForm();
        model.addAttribute("feedbackForm", feedbackForm);

        return "user/feedback";
    }

    @PostMapping("/feedback")
    public String submitFeedback(@Valid @ModelAttribute("feedbackForm") FeedbackForm feedbackForm,
            BindingResult rBindingResult, HttpSession session) {

        if (rBindingResult.hasErrors()) {
            session.setAttribute("message", Message.builder()
                    .content("Please Correct the following errors")
                    .type(MessageType.red)
                    .build());

            return "user/feedback";
        }

        Feedback feedback = new Feedback();
        feedback.setName(feedbackForm.getName());
        feedback.setEmail(feedbackForm.getEmail());
        feedback.setRating(feedbackForm.getRating());
        feedback.setFeedbackText(feedbackForm.getFeedbackText());

        feedbackService.saveFeedback(feedback);

        session.setAttribute("message", Message.builder()
                .content("Your Feedback Submitted Successfully")
                .type(MessageType.green)
                .build());

        return "redirect:/user/feedback";
    }
}
