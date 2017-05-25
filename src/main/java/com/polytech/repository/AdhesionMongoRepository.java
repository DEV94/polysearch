package com.polytech.repository;

import com.polytech.models.Adhesion;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by E.Marouane on 25/05/2017.
 */
public interface AdhesionMongoRepository extends MongoRepository<Adhesion, String> {

    public Adhesion findAdhesionBy(String idUtilisateur);

}
