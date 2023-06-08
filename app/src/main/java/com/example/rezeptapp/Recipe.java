package com.example.rezeptapp;

import java.util.ArrayList;

public class Recipe {
    private String id;
    private String name;
    private String category;
    private String area;
    private String instruction;
    private String imageURL="";
    private String youtubeURL="";
    private ArrayList<String> ingredient;
    private ArrayList<String> measurement;
    private String sourceURL="";




    public Recipe(String id, String name, String category, String area, String instruction, ArrayList<String> ingredient, ArrayList<String> measurement){
        this.id = id;
        this. name = name;
        this.category=category;
        this.area = area;
        this.instruction = instruction;
        this.ingredient=ingredient;
        this.measurement=measurement;
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


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getYoutubeURL() {
        return youtubeURL;
    }

    public void setYoutubeURL(String youtubeURL) {
        this.youtubeURL = youtubeURL;
    }


    public ArrayList<String> getIngredient() {
        return ingredient;
    }

    public void setIngredient(ArrayList<String> ingredient) {
        this.ingredient = ingredient;
    }

    public ArrayList<String> getMeasurement() {
        return measurement;
    }

    public void setMeasurement(ArrayList<String> measurement) {
        this.measurement = measurement;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }
}
