package com.a00n.springsecurityauthform.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a00n.springsecurityauthform.entities.MyUserDetails;
import com.a00n.springsecurityauthform.entities.Todo;
import com.a00n.springsecurityauthform.repositories.TodoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoRestController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public List<Todo> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            return todoRepository.findByUser(userDetails.getUser());
        }
        return null;
    }

    @PostMapping
    public Todo create(@RequestBody Todo todo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        todo.setUser(((MyUserDetails) authentication.getPrincipal()).getUser());
        return todoRepository.save(todo);
    }

}
