package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller

public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model){
        System.out.println("Home Page Handler");
        //sending data to view
        model.addAttribute("name", "A.vamsy krishna");
        model.addAttribute("skill", "Backend Developer");

        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model){
        model.addAttribute("isLogin","true");
        System.out.println("About page loading");
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage(){
        System.out.println("Services page loading");
        return "services";
    }

    @GetMapping("/contact")
    public String contactPage(){
        System.out.println("Contact page loading");
        return "contact";
    }

    // This is Login Page
    @GetMapping("/login")
    public String loginPage(){
        System.out.println("Login page loading");
        return "login";
    }
    

    //This is Registration page
    @GetMapping("/register")
    public String registerPage(Model model){
        System.out.println("Register page loading");

        UserForm userForm=new UserForm();
        //sending default data
        // userForm.setName("Vamsy");
        // userForm.setEmail("chintuvamsy9@gmail.com");
        // userForm.setAbout("Write Something about yourself");
        model.addAttribute("userForm", userForm);
        return "register";
    }

    //Processing Register 

    @RequestMapping(value="/do-register", method=RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult, HttpSession session) {
        System.out.println("Process Registration");
        // Fetch form data
        // UserForm
        System.out.println(userForm);

        // Validate form data
        if(rBindingResult.hasErrors()) {
            return "register";
        }

        //UserForm --> user
        //user Service
        User user=new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilePic("/images/profilepic.jpg");
         
        // save to database
        User savedUser=userService.saveUser(user);

        System.out.println("Saved User");

        // message= "Registration Sucessful"
        //Add the message
       Message message = Message.builder().content("Registration Succesful").type(MessageType.green).build();
        session.setAttribute("message", message);

        //redirect login page
        return "redirect:/register";
    }

    
    
}
