package com.polytech.repository;

import com.polytech.models.Communaute;
import com.polytech.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by dev on 5/24/17.
 */
public interface CommunauteMongoRepository extends MongoRepository<Communaute, String> {

    public Communaute findCommunauteBy(String id);

    public Communaute findCommunauteByResponsable(String responsable);


}
