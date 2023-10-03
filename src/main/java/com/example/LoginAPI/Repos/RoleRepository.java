package com.example.LoginAPI.Repos;

import com.example.LoginAPI.Role;
import com.example.LoginAPI.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, ObjectId> {


    // function to be used to find roles
    Optional<Role> findByAuthority(String authority);
}
