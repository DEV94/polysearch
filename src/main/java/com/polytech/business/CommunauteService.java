package com.polytech.business;

import com.polytech.models.Communaute;
import com.polytech.repository.CommunauteMongoRepository;
import com.polytech.repository.CommunauteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by E.Marouane on 02/05/2017.
 */

@Component
public class CommunauteService {

    @Autowired
    private CommunauteRepository communauteRepository;

    @Autowired
    private CommunauteMongoRepository communauteMongoRepository;

    public void save(Communaute communaute){
        communauteMongoRepository.save(communaute);
    }

    public List<Communaute> selectAll(){
        System.out.println(communauteMongoRepository.findAll().size());
        return communauteMongoRepository.findAll();
    }

}
