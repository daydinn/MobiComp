package com.example.rezeptapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RecipeManager {
    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private OkHttpClient client;
    private Request request;

    public void setUpHttpClient( String url){
        //http request with okhttp (Dependency in gradle Module:app + Permission Internet in Manifest)
        //Set up http client
        client = new OkHttpClient();
        //String url = "https://www.themealdb.com/api/json/v1/1/random.php";
        request = new Request.Builder().url(url).build();
    }

    public ArrayList<Recipe> getRandomRecipe() throws InterruptedException {

        setUpHttpClient("https://www.themealdb.com/api/json/v1/1/random.php");

        //Make http Request
        //CountDownLatch -> Wartet bis Response komplett ist.
        CountDownLatch countDownLatch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String myResponse = response.body().string();
                    recipes=parseRecipe(myResponse);
                    response.close();
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return recipes;
    }
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




}



