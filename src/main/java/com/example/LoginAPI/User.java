package com.example.LoginAPI;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Document(collection = "Filler")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    private ObjectId id;

    private String username;


    // hiding sensitive information
    @com.fasterxml.jackson.annotation.JsonIgnore
    private String password;

    @com.fasterxml.jackson.annotation.JsonIgnore
    private String email;

    @com.fasterxml.jackson.annotation.JsonIgnore
    private Set<Role> authorites;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getAuthorites();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString(){
        return "Id: "+ this.id+ "email: " + this.email+"username: "+this.username+"password: " + this.password+"Authorities: "+this.authorites;
    }
}
