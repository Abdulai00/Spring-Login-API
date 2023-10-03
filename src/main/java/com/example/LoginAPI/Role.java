package com.example.LoginAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;


@Document(collection ="Role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    private ObjectId roleId;

    private String authority;

    public Role(String authority ){
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
