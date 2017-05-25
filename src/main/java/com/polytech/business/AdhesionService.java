package com.polytech.business;

import com.polytech.models.Adhesion;
import com.polytech.repository.AdhesionMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by E.Marouane on 25/05/2017.
 */
@Component
public class AdhesionService {

    @Autowired
    private AdhesionMongoRepository adhesionMongoRepository;

    public void save(Adhesion adhesion){
        List<Adhesion> liste = selectAll();
        for (Adhesion a : liste) {
            if(a.getIdCommunaute() == adhesion.getIdCommunaute() && a.getIdUtilisateur() == adhesion.getIdUtilisateur()){
                System.out.println("Demmande d'adhesion existe dejà. En attente d'approuvation.");
                return;
            }
        }
        adhesionMongoRepository.save(adhesion);
    }

    public List<Adhesion> selectAll(){
        return adhesionMongoRepository.findAll();
    }

    public Adhesion findAdhesion(String idUtilisateur){
        return adhesionMongoRepository.findAdhesionBy(idUtilisateur);
    }

    public void delete(String idAdhesion){
        adhesionMongoRepository.delete(idAdhesion);
    }

}