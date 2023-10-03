package com.example.LoginAPI.configuration;


import com.example.LoginAPI.Utility.RSAKeyConfig;
import com.nimbusds.jose.crypto.impl.RSAKeyUtils;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration // This class is a Spring Boot configuration class.
public class SecurityConfiguration {

    public int $height;
    private final RSAKeyConfig keys; // This field contains the public and private keys that will be used to encrypt and decrypt JWT tokens.

    public SecurityConfiguration(RSAKeyConfig keys) {
        this.keys = keys;
    }

    @Bean // This method creates a bean that will be used to encode passwords.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // This method creates a bean that will be used to authenticate users.
    public AuthenticationManager authManager(UserDetailsService detailsService) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(detailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoProvider);
    }

    @Bean // This method creates a bean that will be used to filter requests and apply security constraints.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable CSRF protection.
        http.csrf(csrf -> csrf.disable());

        // Configure the authorization rules for the application.
        http.authorizeHttpRequests(auth -> auth
                // The `/auth/**` endpoints are accessible to all users.
                .requestMatchers("/auth/**").permitAll()
                // The `/admin/**` endpoints are only accessible to users with the `ADMIN` role.
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // The `/user/**` endpoints are only accessible to users with the `ADMIN` or `USER` role.
                .requestMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                // All other endpoints are only accessible to authenticated users.
                .anyRequest().authenticated());

        // Configure the application to use JWT tokens for authentication.
        http.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

        // Configure the application to use stateless sessions.
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean // This method creates a bean that will be used to decode JWT tokens.
    public JwtDecoder jwtDecoder() {
        System.out.print("decoder");
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    @Bean // This method creates a bean that will be used to encode JWT tokens.
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(keys.getPublicKey())
                .privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

        return new NimbusJwtEncoder(jwks);
    }

    @Bean // This method creates a bean that will be used to convert JWT tokens to Spring Security authentication objects.
    public JwtAuthenticationConverter jwtConverter() {
        System.out.println("jwt converter");
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtconverter = new JwtAuthenticationConverter();
        jwtconverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtconverter;
    }
}
