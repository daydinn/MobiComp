package com.example.rezeptapp;

import java.util.ArrayList;


public class Recipe {
    private boolean favorite = false;
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;
    private boolean veryHealthy;
    private boolean cheap;
    private boolean veryPopular;
    private boolean sustainable;
    private boolean lowFodmap;
    private int weightWatcherSmartPoints;
    private String gaps;
    private int preparationMinutes;
    private int cookingMinutes;
    private int aggregateLikes;
    private int healthScore;
    private ArrayList<ExtendedIngredient> extendedIngredients;
    private int id;
    private String title;
    private int readyInMinutes;
    private int servings;
    private String sourceUrl;
    private String image;
    private String summary;
    private ArrayList<String> cuisines;
    private ArrayList<String> dishTypes;
    private ArrayList<String> diets;
    private String instructions;
    private Nutrition nutrition;

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

    public boolean isVeryHealthy() {
        return veryHealthy;
    }

    public void setVeryHealthy(boolean veryHealthy) {
        this.veryHealthy = veryHealthy;
    }

    public boolean isCheap() {
        return cheap;
    }

    public void setCheap(boolean cheap) {
        this.cheap = cheap;
    }

    public boolean isVeryPopular() {
        return veryPopular;
    }

    public void setVeryPopular(boolean veryPopular) {
        this.veryPopular = veryPopular;
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

    public int getWeightWatcherSmartPoints() {
        return weightWatcherSmartPoints;
    }

    public void setWeightWatcherSmartPoints(int weightWatcherSmartPoints) {
        this.weightWatcherSmartPoints = weightWatcherSmartPoints;
    }

    public String getGaps() {
        return gaps;
    }

    public void setGaps(String gaps) {
        this.gaps = gaps;
    }

    public int getPreparationMinutes() {
        return preparationMinutes;
    }

    public void setPreparationMinutes(int preparationMinutes) {
        this.preparationMinutes = preparationMinutes;
    }

    public int getCookingMinutes() {
        return cookingMinutes;
    }

    public void setCookingMinutes(int cookingMinutes) {
        this.cookingMinutes = cookingMinutes;
    }

    public int getAggregateLikes() {
        return aggregateLikes;
    }

    public void setAggregateLikes(int aggregateLikes) {
        this.aggregateLikes = aggregateLikes;
    }

    public int getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(int healthScore) {
        this.healthScore = healthScore;
    }

    public ArrayList<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(ArrayList<ExtendedIngredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public ArrayList<Nutrition.Nutrients> getMacroNutrients(){
        ArrayList<Nutrition.Nutrients> macroNutrients = new ArrayList<>();
        String[] searched = {"Fat", "Saturated Fat", "Carbohydrates", "Net Carbohydrates", "Sugar"};
        for (Nutrition.Nutrients nut : nutrition.getNutrients()) {
            for (String item:searched) {
                if(nut.getName().equals(item)){
                    macroNutrients.add(nut);
                }
            }
        }
        return macroNutrients;
    }
    public ArrayList<Nutrition.Nutrients> getMicroNutrients(){
        ArrayList<Nutrition.Nutrients> microNutrients = new ArrayList<>();
        String[] searched = {"Cholesterol", "Sodium", "Protein", "Selenium", "Phosphorus", "Iron", "Potassium", "Folate", "Magnesium", "Zinc", "Copper", "Manganese", "Fiber", "Calcium"};
        for (Nutrition.Nutrients nut : nutrition.getNutrients()) {
            for (String item:searched) {
                if(nut.getName().equals(item)){
                    microNutrients.add(nut);
                }
            }
        }
        return microNutrients;
    }
    public ArrayList<Nutrition.Nutrients> getVitamins(){
        ArrayList<Nutrition.Nutrients> vitamins = new ArrayList<>();
        String[] searched = {"Vitamin A", "Vitamin B1", "Vitamin B12", "Vitamin B2", "Vitamin B3", "Vitamin B5", "Vitamin B6", "Vitamin C", "Vitamin D", "Vitamin E", "Vitamin K"};
        for (Nutrition.Nutrients nut : nutrition.getNutrients()) {
            for (String item:searched) {
                if(nut.getName().equals(item)){
                    vitamins.add(nut);
                }
            }
        }
        return vitamins;
    }


    // Inner Ingredient class __________________________________________________________________

    public static class ExtendedIngredient {
        private int id;
        private String name;
        private double amount;
        private String unit;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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
        private ArrayList<Nutrients> nutrients;

        public ArrayList<Nutrients> getNutrients(){
            return nutrients;
        }
        public void setNutrients(ArrayList<Nutrients> nutrients){
            this.nutrients=nutrients;
        }

        public class Nutrients {
            private String name;
            private float amount;
            private String unit;
            private float percentOfDailyNeeds;

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

            public float getPercentOfDailyNeeds() {
                return percentOfDailyNeeds;
            }

            public void setPercentOfDailyNeeds(float percentOfDailyNeeds) {
                this.percentOfDailyNeeds = percentOfDailyNeeds;
            }
        }
    }





}