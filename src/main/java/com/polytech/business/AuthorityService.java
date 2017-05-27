package com.polytech.business;

import com.polytech.models.Authority;
import com.polytech.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by E.Marouane on 26/05/2017.
 */
@Component
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository){
        this.authorityRepository = authorityRepository;
    }

    public void save(Authority authority){
        authorityRepository.save(authority);
    }

}

