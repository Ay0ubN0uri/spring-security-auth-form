package com.a00n.springsecurityauthform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.a00n.springsecurityauthform.entities.MyUserDetails;
import com.a00n.springsecurityauthform.entities.Todo;
import com.a00n.springsecurityauthform.repositories.TodoRepository;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todos")
    public String todos(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        model.addAttribute("todo", new Todo());
        model.addAttribute("todos", todoRepository.findByUser(userDetails.getUser()));
        return "todos";
    }

    @PostMapping("/todos/create")
    public String createTodo(Model model, @ModelAttribute("todo") Todo todo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        todo.setUser(userDetails.getUser());
        todoRepository.save(todo);
        model.addAttribute("todo", new Todo());
        model.addAttribute("todos", todoRepository.findByUser(userDetails.getUser()));
        return "todos";
    }

}
