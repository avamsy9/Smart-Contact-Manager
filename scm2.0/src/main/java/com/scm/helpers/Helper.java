package com.scm.helpers;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {


    public static String getEmailOfLoggedInUser(Authentication authentication) {

        // How to get Email in user/profile
        // Sign in with username & password
        if (authentication instanceof OAuth2AuthenticationToken) {

            var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User) authentication.getPrincipal();
            String username = "";

            // sign with google
            if (clientId.equalsIgnoreCase("google")) {

                System.out.println("Getting email from google");
                username = oauth2User.getAttribute("email").toString();

            } 
            
            // sign with github
            else if (clientId.equalsIgnoreCase("github")) {
                
                System.out.println("Getting email from github");
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                        : oauth2User.getAttribute("login").toString() + "@gmail.com";
            }

            // sign with facebook
            return username;

        } else {
            System.out.println("Getting data from local database");
            return authentication.getName();
        }

    }

    public String getLinkForEmailVerificatiton(String emailToken) {
        
        String link="http://localhost:8081/auth/verify-email?token="+emailToken;
        return link;
    }
}