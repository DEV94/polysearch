package com.polytech.business;

import com.polytech.models.Communaute;
import com.polytech.repository.CommunauteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by E.Marouane on 02/05/2017.
 */

@Component
public class CommunauteService {

    @Autowired
    private CommunauteRepository communauteRepository;

    public void save(Communaute communaute){
        communauteRepository.save(communaute);
    }

}
