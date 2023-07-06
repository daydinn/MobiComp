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

    public boolean isVegetarian() {
        return vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }


    public boolean isGlutenFree() {
        return glutenFree;
    }


    public boolean isDairyFree() {
        return dairyFree;
    }


    public boolean isSustainable() {
        return sustainable;
    }


    public boolean isLowFodmap() {
        return lowFodmap;
    }


    public int getHealthScore() {
        return healthScore;
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


    public int getServings() {
        return servings;
    }


    public String getSourceUrl() {
        return sourceUrl;
    }


    public String getImage() {
        return image;
    }


    public ArrayList<String> getCuisines() {
        return cuisines;
    }


    public String getInstructions() {
        return instructions;
    }


    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    /**
     * Filters classes nutrient value by macro nutrients and returns them.
     * @Author Rene Wentzel
     * @return Returns an Arraylist of Nutrient objects (ArrayList<Nutrition.Nutrient>) of macro nutrients.
     */
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

    /**
     * Filters classes nutrient value by micro nutrients and returns them.
     * @Author Rene Wentzel
     * @return Returns an Arraylist of Nutrient objects (ArrayList<Nutrition.Nutrient>) of micro nutrients.
     */
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

    /**
     * Filters classes nutrient value by vitamins and returns them.
     * @Author Rene Wentzel
     * @return Returns an Arraylist of Nutrient objects (ArrayList<Nutrition.Nutrient>) of vitamins.
     */
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


        public String getUnit() {
            return unit;
        }
    }


    //Inner Nutrition class _______________________________________________

    public static class Nutrition{
        private ArrayList<Nutrient> nutrients = new ArrayList<>();
        public ArrayList<Nutrient> getNutrients(){
            return nutrients;
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


            public String getUnit() {
                return unit;
            }

        }
    }


}