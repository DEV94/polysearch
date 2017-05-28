package com.polytech.business;

import com.polytech.models.Requete;
import com.polytech.models.Result;
import com.polytech.repository.RequetMongoRepository;
import com.polytech.repository.elastic.ResultatElasticRepository;
import com.polytech.repository.ResultatMongoRepository;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void saveRequete(Requete requete){
        requetMongoRepository.save(requete);
    }

    public void saveResultat(Result result){
        resultatMongoRepository.save(result);
    }

    public List<Requete> getResults(String title){

        GetRequestBuilder getRequestBuilder = client().prepareGet(indexName, type, id);

        getRequestBuilder.setFields(new String[]{"name"});

        GetResponse response = getRequestBuilder.execute().actionGet();

        String name = response.field("name").getValue().toString();

        List<Requete> liste = new ArrayList<>();
        while (resultatElasticRepository.findAll().iterator().hasNext()){
            liste.add(resultatElasticRepository.findAll().iterator().next());
        }
        return liste;
    }
}
