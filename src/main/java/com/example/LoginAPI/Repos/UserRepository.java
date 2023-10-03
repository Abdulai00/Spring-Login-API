package com.example.LoginAPI.Repos;

import com.example.LoginAPI.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {


    // function to be used to find user objects
    Optional<User> findByUsername(String username);


    boolean existsByUsername(String johndoe);
}
