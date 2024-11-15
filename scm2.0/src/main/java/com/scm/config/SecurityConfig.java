package com.scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scm.services.impl.SecurityCustomerUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /* 
   // Creating user and login using java code with in memory service

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1=User
                .withDefaultPasswordEncoder()
                .username("admin123")
                .password("admin123")
                .roles("ADMIN","USER")
                .build();
        UserDetails user2=User
                .withDefaultPasswordEncoder()
                .username("user123")
                .password("user123")
                .build();
        var inMemoryUserDetailsmanager= new InMemoryUserDetailsManager(user1,user2);
        return inMemoryUserDetailsmanager;
    }
    */

    //fetching user and login details from database

    @Autowired
    private SecurityCustomerUserDetailService userDetailService;

    @Autowired
    private OAuthAuthenticationSucessHandler handler;

    @Autowired
    private AuthFailureHandler authFailureHandler;

    // Configuration of Authentication provider spring security
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
        // need user detail service object
        daoAuthenticationProvider.setUserDetailsService(userDetailService);

        // need password encoder object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        // Configuration
        // urls are cofigured which should be their for public and private
        httpSecurity.authorizeHttpRequests(authorize -> {
            // authorize.requestMatchers("/home","/register","/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        // Default Form Login Page
        // httpSecurity.formLogin(Customizer.withDefaults());

        // Custom Form  Login page
        // If there is any change we have to do related during Form login code

       
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            //formLogin.successForwardUrl("/user/profile");
            //formLogin.failureForwardUrl("/login?error=true");

            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

            formLogin.successHandler(new AuthenticationSuccessHandler() {
                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                    Authentication authentication) throws IOException, ServletException {
                    response.sendRedirect("/user/profile"); // Redirect to the dashboard after successful login
                }
            });

            formLogin.failureHandler(authFailureHandler);

        });

         httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.logout(logoutForm ->{
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        // OAuth Configurations
        // Default OAuth Login page
        //httpSecurity.oauth2Login(Customizer.withDefaults());

        // Custom login page
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });

        return httpSecurity.build();
    }

    
}
