package com.polytech.repository;

import com.polytech.models.Result;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by dev on 5/24/17.
 */
public interface ResultatMongoRepository extends MongoRepository<Result, String> {
}
