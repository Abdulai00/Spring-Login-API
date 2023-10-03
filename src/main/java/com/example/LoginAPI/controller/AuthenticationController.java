package com.example.LoginAPI.controller;


import com.example.LoginAPI.LoginResponseDTO.LoginDTO;
import com.example.LoginAPI.RegisterDTO.DTO;
import com.example.LoginAPI.Services.AuthenticationService;
import com.example.LoginAPI.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    // end point to register users
    @PostMapping("/register")
    public User registerUser(@RequestBody DTO body){
        return authenticationService.registerUser(body.getEmail(), body.getUsername(), body.getPassword());

    }
    // end point to login users
    @PostMapping("/login")
    public LoginDTO LoginUser(@RequestBody DTO body){
        return  authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}
