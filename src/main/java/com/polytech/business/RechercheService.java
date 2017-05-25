package com.polytech.business;

import com.polytech.models.Requete;
import com.polytech.models.Result;
import com.polytech.repository.RequetMongoRepository;
import com.polytech.repository.ResultatMongoRepository;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dev on 5/24/17.
 */
@Component
public class RechercheService {
    @Autowired
    private RequetMongoRepository requetMongoRepository;

    @Autowired
    private ResultatMongoRepository resultatMongoRepository;

    public void saveRequete(Requete requete){
        requetMongoRepository.save(requete);
    }

    public void saveResultat(Result result){
        resultatMongoRepository.save(result);
    }
}
