package com.polytech.repository;

import com.polytech.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dev on 4/11/17.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
