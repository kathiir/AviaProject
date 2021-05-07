package com.example.aviaapplication.api.models;

import java.net.URL;

public class User {
    private Long id;

    private String name;
    private URL imageURL;

    public User(String name){
        this.name = name;
    }

    public User(Long id, String name, URL imageURL) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getImageURL() {
        return imageURL;
    }

    public void setImageURL(URL imageURL) {
        this.imageURL = imageURL;
    }
}
