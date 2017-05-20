package com.polytech.models;

/**
 * Created by E.Marouane on 20/05/2017.
 */
public class Result {

    private String title;
    private String uri;


    public Result(String title, String uri) {
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
}
