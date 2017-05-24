package com.polytech.models;

/**
 * Created by E.Marouane on 20/05/2017.
 */
public class Result {

    private double id;
    private String title;
    private String uri;
    private double rating;


    public Result(double id, String title, String uri) {
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

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
