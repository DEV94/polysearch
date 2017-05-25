package com.polytech.models;

import org.springframework.data.annotation.*;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.*;

/**
 * Created by E.Marouane on 02/05/2017.
 */

@Entity
@Table(name = "communaute")
public class Communaute {

    @Id
    @org.springframework.data.annotation.Id
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

    @Transient
    private List<User> personnes;

    public Communaute() {
        personnes = new ArrayList<>();
        dateCreation = new GregorianCalendar().getTime();
    }

    public Communaute(String nom, String responsable, String description) {
        this.nom = nom;
        this.description = description;
        personnes = new ArrayList<>();
        dateCreation = new GregorianCalendar().getTime();
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

    public void setId(String id) {
        this.id = id;
    }
}
