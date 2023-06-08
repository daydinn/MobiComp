package com.example.rezeptapp;

public class ShortRecipe {

    private String name;
    private String id;
    private String imageURL;

    public ShortRecipe(String id, String name, String imageURL){
        this.id=id;
        this.name=name;
        this.imageURL=imageURL;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
