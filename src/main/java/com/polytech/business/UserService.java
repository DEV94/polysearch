package com.polytech.business;

import com.polytech.models.Authority;
import com.polytech.models.User;
import com.polytech.repository.UserMongoRepository;
import com.polytech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by E.Marouane on 25/05/2017.
 */
@Component
public class UserService {

    @Autowired
    private UserMongoRepository userMongoRepository;

    @Autowired
    private UserRepository userRepository;

    public void save(User user){
        userRepository.save(user);
        userMongoRepository.save(user);
    }

    public User findUserByUsername(String username){
        return userMongoRepository.findByusername(username);
    }

    public List<User> findUserByidCommunaute(String idCommunaute){
        return userMongoRepository.findUserByidCommunaute(idCommunaute);
    }

}
