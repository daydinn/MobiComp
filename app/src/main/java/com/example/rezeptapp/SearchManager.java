package com.example.rezeptapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SearchManager {
    private ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
    private ArrayList<ShortRecipe> shortRecipeList = new ArrayList<>();
    private OkHttpClient client;
    private Request request;

    public void setUpHttpClient( String url){
        //http request with okhttp
        //Set up http client
        client = new OkHttpClient();
        request = new Request.Builder().url(url).build();
    }

    //Parse Recipes
    public ArrayList<Recipe> parseRecipe(String jsonString){
        ArrayList<Recipe> newRecipes = new ArrayList<Recipe>();

        //parse json
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("meals");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonInside = jsonArray.getJSONObject(i);
                ArrayList<String> ingredient= new ArrayList<String>();
                ArrayList<String> measurement= new ArrayList<String>();
                for(int j=0; j<20; j++){ //20 = max number of ingredients
                    if(!jsonInside.getString("strIngredient"+(j + 1)).isEmpty() && !jsonInside.getString("strMeasure"+(j + 1)).isEmpty()){
                        ingredient.add(jsonInside.getString("strIngredient"+(j + 1)));
                        measurement.add(jsonInside.getString("strMeasure" + (j + 1)));
                    }
                }
                //create Recipe
                newRecipes.add(new Recipe(jsonInside.getString("idMeal"),
                        jsonInside.getString("strMeal"),
                        jsonInside.getString("strCategory"),
                        jsonInside.getString("strArea"),
                        jsonInside.getString("strInstructions"),
                        ingredient,
                        measurement
                ));
                if(!jsonInside.getString("strMealThumb").isEmpty()){
                    newRecipes.get(newRecipes.size() - 1).setImageURL(jsonInside.getString("strMealThumb"));
                }
                if(!jsonInside.getString("strYoutube").isEmpty()){
                    newRecipes.get(newRecipes.size() - 1).setYoutubeURL(jsonInside.getString("strYoutube"));
                }
                if(!jsonInside.getString("strSource").isEmpty()){
                    newRecipes.get(newRecipes.size() - 1).setSourceURL(jsonInside.getString("strSource"));
                }
            }
        } catch (JSONException e) {
            Log.e("Error","Error loading data.");
            throw new RuntimeException(e);
        }
        return newRecipes;
    }

    //Parse Short Recipes
    public ArrayList<ShortRecipe> parseShortRecipe(String jsonString){
        ArrayList<ShortRecipe> newRecipes = new ArrayList<>();

        //parse json
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("meals");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonInside = jsonArray.getJSONObject(i);
                newRecipes.add(new ShortRecipe(jsonInside.getString("idMeal"),
                        jsonInside.getString("strMeal"),
                        jsonInside.getString("strMealThumb")
                ));
            }
        } catch (JSONException e) {
            Log.e("Error","Error loading data.");
            throw new RuntimeException(e);
        }
        return newRecipes;
    }


    public ArrayList<Recipe> getRecipeFromAPI(String url) throws InterruptedException {
        recipeList = new ArrayList<>();
        //Set Up Http Client
        setUpHttpClient(url);
        //CountDownLatch -> Wartet bis Response komplett ist.
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //Make http Request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                recipeList=new ArrayList<Recipe>();
                e.printStackTrace();
                countDownLatch.countDown();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()&&response.body()!=null){
                    String myResponse = response.body().string();
                    recipeList=parseRecipe(myResponse);
                    response.close();
                    countDownLatch.countDown();
                }
                else {
                    Log.d("error", response.message());
                }
            }
        });
        countDownLatch.await();
        return recipeList;
    }

    public ArrayList<ShortRecipe> getShortRecipeFromAPI(String url)throws InterruptedException {
        shortRecipeList = new ArrayList<>();
        //Set Up Http Client
        setUpHttpClient(url);
        //CountDownLatch -> Wartet bis Response komplett ist.
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //Make http Request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                shortRecipeList = new ArrayList<ShortRecipe>();
                e.printStackTrace();
                countDownLatch.countDown();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()&&response.body()!=null){
                    String myResponse = response.body().string();
                    shortRecipeList=parseShortRecipe(myResponse);
                    response.close();
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return shortRecipeList;

    }

    //Liste an Kategorien/Gebieten/Zutaten
    public ArrayList<String> getListfromAPI(int category) throws InterruptedException {
        ArrayList<String> newList = new ArrayList<>();
        String url="https://www.themealdb.com/api/json/v1/1/list.php?a=list";
        String keyword = "strArea";
        if(category!=0 && category!=1 && category!=2){
            return newList;
        }
        switch (category){
            //Category
            case 0:
                url="https://www.themealdb.com/api/json/v1/1/list.php?c=list";
                keyword="strCategory";
                break;
            //Area
            case 1:
                url="https://www.themealdb.com/api/json/v1/1/list.php?a=list";
                keyword = "strArea";
                break;
            //Ingredients
            case 2:
                url="https://www.themealdb.com/api/json/v1/1/list.php?i=list";
                keyword="strIngredient";
                break;
        }
        String finalKeyword = keyword;
        //Set Up Http Client
        setUpHttpClient(url);
        //CountDownLatch -> Wartet bis Response komplett ist.
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //Make http Request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                //shortRecipeList = new ArrayList<ShortRecipe>();
                e.printStackTrace();
                countDownLatch.countDown();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()&&response.body()!=null){
                    String myResponse = response.body().string();
                    //parse json
                    try {
                        JSONObject jsonObject = new JSONObject(myResponse);
                        JSONArray jsonArray = jsonObject.getJSONArray("meals");
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonInside = jsonArray.getJSONObject(i);
                            newList.add(jsonInside.getString(finalKeyword));
                        }
                    } catch (JSONException e) {
                        Log.e("Error","Error loading data.");
                        throw new RuntimeException(e);
                    }
                    response.close();
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return newList;
    }




    //Abfrage: Zufallsrezept
    //Rezept
    public ArrayList<Recipe> getRandomRecipe() throws InterruptedException {
        return getRecipeFromAPI("https://www.themealdb.com/api/json/v1/1/random.php");
    }

    //Abfrage: Rezepte nach Namen
    //Rezept
    public ArrayList<Recipe> getRecipesByName(String name) throws InterruptedException {
        return getRecipeFromAPI("https://www.themealdb.com/api/json/v1/1/search.php?s="+name);
    }

    //Abfrage: Rezepte nach Zutaten
    //Liste
    public ArrayList<ShortRecipe> getRecipesByIngredient(String ingredient) throws InterruptedException {
        return getShortRecipeFromAPI("https://www.themealdb.com/api/json/v1/1/filter.php?i="+ingredient);
    }

    //Abfrage: Rezepte nach Kategorie
    //Liste
    public ArrayList<ShortRecipe> getRecipesByCategory(String category) throws InterruptedException {
        return getShortRecipeFromAPI("https://www.themealdb.com/api/json/v1/1/filter.php?c="+category);
    }

    //Abfrage: Rezepte nach Gebiet
    //Liste
    public ArrayList<ShortRecipe> getRecipesByArea(String area) throws InterruptedException {
        return getShortRecipeFromAPI("www.themealdb.com/api/json/v1/1/filter.php?a="+area);
    }

    //Rezept nach ID
    //Rezept
    public Recipe getRecipesByID(String id) throws InterruptedException {
        return getRecipeFromAPI("www.themealdb.com/api/json/v1/1/lookup.php?i="+id).get(0);
    }








    //Suche nach mehreren Zutaten
    //Liste
    public ArrayList<ShortRecipe> getRecipesByMultipleIngredients(ArrayList<String> ingredients) throws InterruptedException {
        ArrayList<ShortRecipe> newRecipes = new ArrayList<>();
        for(int i=0; i<ingredients.size(); i++){
            newRecipes.addAll(getRecipesByIngredient(ingredients.get(i)));
        }
        ArrayList<String> idList = new ArrayList<>();
        for(int i=0;i<newRecipes.size();i++){
            idList.add(newRecipes.get(i).getId());
        }
        //Filtere die Rezepte heraus, die alle Zutaten enthalten.
        ArrayList<ShortRecipe> recipeSet = new ArrayList<>();
        for (ShortRecipe loopRecipe: newRecipes) {
            //Wenn das aktuelle Rezept genau so oft in der newRecipe Liste enthalten ist wie es Zutaten gibt
            if(Collections.frequency(idList, loopRecipe.getId())==ingredients.size()){
                //Gibt noch Duplikate zurück
                recipeSet.add(loopRecipe);
            }
        }
        return recipeSet;
    }


    //Unerwünschte Zutaten herausfiltern
    public ArrayList<Recipe> filterUnwantedIngredients(ArrayList<Recipe> recipeList, ArrayList<String> ingredientList){
        ArrayList<Recipe> newRecipeList = new ArrayList<>();
        for(int i=0; i<recipeList.size(); i++){
            for(int j=0; j<ingredientList.size(); j++){
                if(recipeList.get(i).getIngredient().contains(ingredientList.get(j))){
                    recipeList.remove(i);
                }
            }
        }


        return newRecipeList;
    }


    //Duplikate entfernen


    //Suche mit mehreren Parametern

}



