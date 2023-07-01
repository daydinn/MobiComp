package com.example.rezeptapp;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class Recipe implements Serializable {
    private boolean favorite = false;
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;
    private boolean sustainable;
    private boolean lowFodmap;
    private int healthScore;
    private int id;
    private String title;
    private int readyInMinutes;
    private int servings;
    private String sourceUrl;
    private String image;
    private ArrayList<String> cuisines = new ArrayList<>();
    private ArrayList<String> dishTypes = new ArrayList<>();
    private ArrayList<String> diets = new ArrayList<>();
    private String instructions;
    @SerializedName("nutrition")
    private Nutrition nutrition = new Nutrition();
    @SerializedName("extendedIngredients")
    private ArrayList<Ingredient> ingredientList = new ArrayList<>();

    public Recipe() {
    }

    /**
     * Returns whether the recipe is vegetarian or not.
     * @Author Rene Wentzel
     * @return boolean
     */
    public boolean isVegetarian() {
        return vegetarian;
    }

    /**
     * Sets the state of the recipe being vegetarian or not.
     * @Author Rene Wentzel
     * @param vegetarian boolean
     */
    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    /**
     * Returns whether the recipe is vegean or not.
     * @Author Rene Wentzel
     * @return boolean
     */
    public boolean isVegan() {
        return vegan;
    }

    /**
     * Sets the state of the recipe being vegan or not.
     * @Author Rene Wentzel
     * @param vegan boolean
     */
    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public void setDairyFree(boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    public boolean isSustainable() {
        return sustainable;
    }

    public void setSustainable(boolean sustainable) {
        this.sustainable = sustainable;
    }

    public boolean isLowFodmap() {
        return lowFodmap;
    }

    public void setLowFodmap(boolean lowFodmap) {
        this.lowFodmap = lowFodmap;
    }

    public int getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(int healthScore) {
        this.healthScore = healthScore;
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

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getCuisines() {
        return cuisines;
    }

    public void setCuisines(ArrayList<String> cuisines) {
        this.cuisines = cuisines;
    }

    public ArrayList<String> getDishTypes() {
        return dishTypes;
    }

    public void setDishTypes(ArrayList<String> dishTypes) {
        this.dishTypes = dishTypes;
    }

    public ArrayList<String> getDiets() {
        return diets;
    }

    public void setDiets(ArrayList<String> diets) {
        this.diets = diets;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public ArrayList<Nutrition.Nutrient> getMacroNutrients(){
        ArrayList<Nutrition.Nutrient> macroNutrients = new ArrayList<>();
        String[] searched = {"Fat", "Saturated Fat", "Carbohydrates", "Net Carbohydrates", "Sugar"};
        for (String s: searched) {
            for (Nutrition.Nutrient nut: nutrition.getNutrients()) {
                if(nut.getName().equals(s)){
                    macroNutrients.add(nut);
                }
            }

        }
        return macroNutrients;
    }
    public ArrayList<Nutrition.Nutrient> getMicroNutrients(){
        ArrayList<Nutrition.Nutrient> microNutrients = new ArrayList<>();
        String[] searched = {"Cholesterol", "Sodium", "Protein", "Selenium", "Phosphorus", "Iron", "Potassium", "Folate", "Magnesium", "Zinc", "Copper", "Manganese", "Fiber", "Calcium"};
        for (String s: searched) {
            for (Nutrition.Nutrient nut: nutrition.getNutrients()) {
                if(nut.getName().equals(s)){
                    microNutrients.add(nut);
                }
            }

        }
        return microNutrients;
    }
    public ArrayList<Nutrition.Nutrient> getVitamins(){
        ArrayList<Nutrition.Nutrient> vitamins = new ArrayList<>();
        String[] searched = {"Vitamin A", "Vitamin B1", "Vitamin B2", "Vitamin B3", "Vitamin B5", "Vitamin B6", "Vitamin B12", "Vitamin C", "Vitamin D", "Vitamin E", "Vitamin K"};
        for (String s: searched) {
            for (Nutrition.Nutrient nut: nutrition.getNutrients()) {
                if(nut.getName().equals(s)){
                    vitamins.add(nut);
                }
            }

        }
        return vitamins;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }


    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }


    // Inner Ingredient class __________________________________________________________________

    public static class Ingredient {
        private String name;
        private double amount;
        private String unit;



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }


    //Inner Nutrition class _______________________________________________

    public static class Nutrition{
        private ArrayList<Nutrient> nutrients = new ArrayList<>();
        public ArrayList<Nutrient> getNutrients(){
            return nutrients;
        }
        public void setNutrients(ArrayList<Nutrient> nutrients){
            this.nutrients=nutrients;
        }
        public  class Nutrient {
            private String name;
            private float amount;
            private String unit;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public float getAmount() {
                return amount;
            }

            public void setAmount(float amount) {
                this.amount = amount;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }
        }
    }


}