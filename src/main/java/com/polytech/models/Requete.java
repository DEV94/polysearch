package com.polytech.models;

import javax.persistence.*;

/**
 * Created by E.Marouane on 12/05/2017.
 */
@Entity
@Table(name = "requetes")
public class Requete {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private double id;

    @Column(name = "query")
    private String query;

    @Column(name = "username")
    private String username;

    public Requete() {

    }

    public Requete(double id, String query, String username) {
        this.id = id;
        this.query = query;
        this.username = username;
    }

    public double getId() {
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
