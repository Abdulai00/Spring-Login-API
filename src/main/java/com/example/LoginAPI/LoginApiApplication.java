package com.example.LoginAPI;

import com.example.LoginAPI.Repos.RoleRepository;
import com.example.LoginAPI.Repos.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;



@SpringBootApplication
public class LoginApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginApiApplication.class, args);
	}


	// intitalizing an admin user with augmented controls, this type of user can access any endpoint
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;{

			}
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User(new ObjectId(),"admin",passwordEncoder.encode("password"),"admin@admin.com",roles);

			userRepository.save(admin);
		};
	}

}
