package com.example.LoginAPI.RegisterDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DTO {
    private String email;

    private String username;

    private String password;


    @Override
    public String toString(){
        return "Registration info:\n username:"+ this.username +"email: "+this.email + "password: " + this.password;
    }
}
