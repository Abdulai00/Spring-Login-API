package com.example.LoginAPI.Services;


import com.example.LoginAPI.LoginResponseDTO.LoginDTO;
import com.example.LoginAPI.Repos.RoleRepository;
import com.example.LoginAPI.Repos.UserRepository;
import com.example.LoginAPI.Role;
import com.example.LoginAPI.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import java.util.HashSet;
import java.util.Set;



@Service
@Transactional
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;


    public User registerUser(String email, String username, String password) {


        // Checking to see if a user already exists

        boolean exists = userRepository.existsByUsername(username);

        if (exists) {
            return null;
        }

        String encodePassword = encoder.encode(password);

        // getting the correct role from users
        Role role = roleRepository.findByAuthority("USER").get();

        // making the set to hold user roles
        Set<Role> authorities = new HashSet<>();

        authorities.add(role);

        // adding the user to the database
        return userRepository.save(new User(new ObjectId()
                , username, encodePassword, email, authorities));
    }

    public LoginDTO loginUser(String username, String password) {

        // authenticating the user
        try {
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));


            String token1 = tokenService.generateJwt(auth);

            return new LoginDTO(userRepository.findByUsername(username).get(), token1);

        } catch (AuthenticationException e) {
            return new LoginDTO(null, "");
        }


    }

}
