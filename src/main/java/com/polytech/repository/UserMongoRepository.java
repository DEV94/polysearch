package com.polytech.repository;

import com.polytech.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dev on 5/23/17.
 */
public interface UserMongoRepository extends MongoRepository<User, String> {

        List<User> findByusername(String username);
}