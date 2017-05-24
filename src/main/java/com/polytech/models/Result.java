package com.polytech.models;

import org.springframework.data.annotation.Id;

/**
 * Created by E.Marouane on 20/05/2017.
 */
public class Result {

    @Id
    private String id;

    private String title;

    private String uri;

    private double rating;


    public Result(String id, String title, String uri) {
        this.id = id;
        this.title = title;
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
