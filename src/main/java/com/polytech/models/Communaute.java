package com.polytech.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by E.Marouane on 02/05/2017.
 */

@Entity
@Table(name = "communaute")
public class Communaute {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "responsable")
    private String responsable;

    @Column(name = "description")
    private String description;

    private Date dateCreation;

    private List<User> personnes;

    public Communaute() {
    }

    public Communaute(String nom, String responsable, String description) {
        this.nom = nom;
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsableID(String responsable) {
        this.responsable = responsable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getPersonnes() {
        return personnes;
    }

    public void setPersonnes(List<User> personnes) {
        this.personnes = personnes;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
