package com.a00n.springsecurityauthform.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.a00n.springsecurityauthform.entities.User;
import com.a00n.springsecurityauthform.repositories.RoleRepository;
import com.a00n.springsecurityauthform.repositories.UserRepository;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String index() {
        // Authentication authentication =
        // SecurityContextHolder.getContext().getAuthentication();
        // if (authentication != null && authentication.isAuthenticated()) {
        // System.out.println("======================================");
        // System.out.println(authentication.getPrincipal());
        // return "dashboard";
        // }
        return "index";
    }

    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(Model model, @ModelAttribute("user") User user) {
        System.out.println("=======================================================================================");
        System.out.println(user);
        if (userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).isPresent()) {
            model.addAttribute("error", "User already exists");
            return "signup";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var userRole = roleRepository.findByName("ROLE_USER").orElse(null);
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        return "login";
    }

}
