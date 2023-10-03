package com.example.LoginAPI.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {


    // endpoint to simulate user access
    @GetMapping("/")
    public String helloUserController(){
        System.out.println("UserController");

        return "User access level";
    }
}
