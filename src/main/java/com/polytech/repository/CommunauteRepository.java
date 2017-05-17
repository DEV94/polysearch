package com.polytech.repository;

import com.polytech.models.Authority;
import com.polytech.models.Communaute;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by E.Marouane on 02/05/2017.
 */
public interface CommunauteRepository extends JpaRepository<Communaute, Integer> {
}
