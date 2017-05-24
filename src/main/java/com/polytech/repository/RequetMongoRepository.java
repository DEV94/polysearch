package com.polytech.repository;

import com.polytech.models.Requete;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by dev on 5/24/17.
 */
public interface RequetMongoRepository extends MongoRepository<Requete, String> {
}
