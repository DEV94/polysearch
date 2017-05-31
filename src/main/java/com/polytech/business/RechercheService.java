package com.polytech.business;

import com.polytech.models.Communaute;
import com.polytech.models.Requete;
import com.polytech.models.Result;
import com.polytech.models.User;
import com.polytech.repository.*;
import com.polytech.repository.elastic.ResultatElasticRepository;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 5/24/17.
 */
@Component
public class RechercheService {
    @Autowired
    private RequetMongoRepository requetMongoRepository;

    @Autowired
    private ResultatMongoRepository resultatMongoRepository;

    @Autowired
    private ResultatElasticRepository resultatElasticRepository;

    @Autowired
    private UserMongoRepository userMongoRepository;

    @Autowired
    private CommunauteMongoRepository communauteMongoRepository;




    public void saveRequete(Requete requete){
        requetMongoRepository.save(requete);
    }

    public void saveResultat(Result result){
        resultatMongoRepository.save(result);
    }

    public List<Result> getResultsElastic(String title){
        return resultatElasticRepository.findResultsByTitleLike(title, new Sort(Sort.Direction.DESC, "rating"));
    }

    public List<Result> getResultsMongo(String title, String idCommunaute){
        List<Result> results = resultatMongoRepository.findResultsByTitleLike(title, new Sort(Sort.Direction.DESC, "rating"));
        List<Result> resultList = new ArrayList<>();
        for (Result result: results) {
            Requete req = requetMongoRepository.findOne(result.getRequete());
            User user = userMongoRepository.findByusername(req.getUsername());
            if(user.getIdCommunaute().equals(idCommunaute)){
                resultList.add(result);
            }
        }
        return  resultList;
    }

    public List<Requete> getRequeteMongo(String idCommunaute){
        List<Requete> requetes = requetMongoRepository.findAll();
        List<Requete> resultList = new ArrayList<>();
        for (Requete requete: requetes) {
            User user = userMongoRepository.findByusername(requete.getUsername());
            if(user.getIdCommunaute().equals(idCommunaute)){
                resultList.add(requete);
            }
        }
        return  resultList;
    }
}
