package com.example.rezeptapp;

import java.io.Serializable;

public class ShortInfo implements Serializable {

    private int id;
    private String title;
    private String image;
    private boolean favorite = false;

    public ShortInfo(int id, String title, String image){
        this.id=id;
        this.title = title;
        this.image = image;
    }


    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
