package com.ticketingSystem.TicketingSimulation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class controoler {

// make an object of the repository


    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
@PostMapping("/task")
    public List<String> post(){
        //https requests
    //repository do the CRUD operations (with the database)
    //JPA IS A interface



   // repository.save(related Entity)
    List<String>user = new ArrayList<String>();
    user.add("John");
    user.add("Mary");
    user.add("Bob");
        return user;
}
}
