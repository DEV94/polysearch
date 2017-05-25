package com.polytech.repository;

import com.polytech.models.Communaute;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by dev on 5/24/17.
 */
public interface CommunauteMongoRepository extends MongoRepository<Communaute, String> {

    public Communaute findCommunauteBy(String id);

}
