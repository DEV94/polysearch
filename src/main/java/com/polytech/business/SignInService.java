package com.polytech.business;


import com.polytech.models.Authority;
import com.polytech.models.User;
import com.polytech.repository.AuthorityRepository;
import com.polytech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dev on 4/11/17.
 */
@Component
public class SignInService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    public void signIn(User user){
        userRepository.save(user);
        authorityRepository.save(new Authority(user.getUsername(),"USER"));
    }
}
