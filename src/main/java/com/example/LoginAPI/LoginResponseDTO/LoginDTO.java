package com.example.LoginAPI.LoginResponseDTO;

import com.example.LoginAPI.User;
import lombok.Data;


@Data
public class LoginDTO {
    private User user;

    private String jwt;

    public LoginDTO(){
        super();
    }

    public LoginDTO(User user, String jwt){
        this.user = user;
        this.jwt = jwt;

    }
}
