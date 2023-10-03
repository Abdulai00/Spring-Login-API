package com.example.LoginAPI.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    // endpoint to simulate admin access
    @GetMapping("/")
    public String HelloAdminController(){
        return "Admin level Access";
    }
}
