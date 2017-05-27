package com.polytech.business;

import com.polytech.models.Authority;
import com.polytech.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by E.Marouane on 26/05/2017.
 */
@Component
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public void save(Authority authority) {
        authorityRepository.save(authority);
    }

    public List<Authority> selectAll() {
        return authorityRepository.findAll();
    }

    public Authority getAuthorityByUsername(String username) {
        return authorityRepository.getAuthorityByUsername(username);
    }

}