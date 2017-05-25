package com.polytech.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by E.Marouane on 25/05/2017.
 */
public class Adhesion {

    @org.springframework.data.annotation.Id
    private String id;

    private String idCommunaute;

    private String idUtilisateur;

    public Adhesion(){

    }

    public Adhesion(String idCommunaute, String idUtilisateur){
        this.idCommunaute = idCommunaute;
        this.idUtilisateur = idUtilisateur;
    }


    public String getId() {
        return id;
    }

    public String getIdCommunaute() {
        return idCommunaute;
    }

    public void setIdCommunaute(String idCommunaute) {
        this.idCommunaute = idCommunaute;
    }

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}
