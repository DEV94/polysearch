package com.polytech.repository;

import com.polytech.models.Result;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by dev on 5/24/17.
 */
public interface ResultatMongoRepository extends MongoRepository<Result, String> {

    public List<Result> findResultsByTitleLike(String title, Sort sort);

}
