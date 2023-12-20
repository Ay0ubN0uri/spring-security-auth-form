package com.a00n.springsecurityauthform.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.a00n.springsecurityauthform.entities.MyUserDetails;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/csrf-totken")
    public CsrfToken csrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/a00n")
    public String a00n() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            MyUserDetails user = (MyUserDetails) authentication.getPrincipal();
            System.out.println(user);
            return "Hello, " + authentication.getName() + "!";
        } else {
            return "Not authenticated";
        }
    }

}
