package com.a00n.springsecurityauthform.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.a00n.springsecurityauthform.entities.User;
import com.a00n.springsecurityauthform.repositories.RoleRepository;
import com.a00n.springsecurityauthform.repositories.UserRepository;

@RestController
public class UserRestController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++");
        System.out.println(user);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var userRole = roleRepository.findByName("ROLE_USER").orElse(null);
        user.setRoles(Set.of(userRole));
        return ResponseEntity.ok(userRepository.save(user));
    }

}
