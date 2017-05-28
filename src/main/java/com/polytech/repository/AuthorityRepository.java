package com.polytech.repository;

import com.polytech.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dev on 4/12/17.
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    public Authority getAuthorityByUsername(String username);

}
