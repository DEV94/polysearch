package com.polytech.models;

import org.springframework.data.annotation.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.persistence.Id;

/**
 * Created by E.Marouane on 12/05/2017.
 */
@Entity
@Table(name = "requetes")
public class Requete {

    @Id
    @org.springframework.data.annotation.Id
    @Column(name = "ID")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "query")
    private String query;

    @Column(name = "username")
    private String username;

    public Requete() {

    }

    public Requete(String id, String query, String username) {
        this.id = id;
        this.query = query;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
