package com.example.rezeptapp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SearchManager {
    private Recipe recipe = new Recipe();
    ArrayList<Recipe> recipeList = new ArrayList<>();
    private ArrayList<ShortInfo> shortInfoList = new ArrayList<>();
    private OkHttpClient client;
    private Request request;
    private String apiKey = "?apiKey=3a12fe000cda4985aec81ce863d180d9";

    public void setUpHttpClient(String url) {
        //http request with okhttp
        //Set up http client
        client = new OkHttpClient();
        request = new Request.Builder().url(url).build();
    }

    public ArrayList<ShortInfo> searchRecipes(HashMap<String, String> general, HashMap<String, String> macronutrients, HashMap<String, String> micronutrients, HashMap<String, String> vitamins) throws InterruptedException {
        shortInfoList = new ArrayList<>();
        String url = "https://api.spoonacular.com/recipes/complexSearch"+apiKey+ buildURL(general, macronutrients, micronutrients, vitamins);
        Log.d("search", url);
        //Set Up Http Client
        setUpHttpClient(url);
        //CountDownLatch -> Wartet bis Response komplett ist.
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //Make http Request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                shortInfoList = new ArrayList<ShortInfo>();
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String myResponse = response.body().string();
                    //shortInfoList=parseShortRecipe(myResponse);
                    Log.d("Json Parse", myResponse);
                    JsonObject jo = (JsonObject) JsonParser.parseString(myResponse);
                    JsonArray jsonArr = jo.getAsJsonArray("results");
                    Gson gson = new Gson();
                    shortInfoList = gson.fromJson(jsonArr, new TypeToken<ArrayList<ShortInfo>>() {
                    }.getType());
                    for (int i = 0; i < shortInfoList.size(); i++) {
                        Log.d("Json Parse", shortInfoList.get(i).getTitle());
                        Log.d("Json Parse", String.valueOf(shortInfoList.get(i).getId()));
                        Log.d("Json Parse", shortInfoList.get(i).getImage());
                    }
                    response.close();
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return shortInfoList;
    }

    public ArrayList<ShortInfo> getSimilarRecipe(int id, int amount) throws InterruptedException {
        shortInfoList = new ArrayList<>();
        String url = "https://api.spoonacular.com/recipes/" + id + "/similar" + apiKey + "&number=" + amount;
        Log.d("search", url);
        //Set Up Http Client
        setUpHttpClient(url);
        //CountDownLatch -> Wartet bis Response komplett ist.
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //Make http Request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                shortInfoList = new ArrayList<ShortInfo>();
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String myResponse = response.body().string();
                    //shortInfoList=parseShortRecipe(myResponse);
                    Log.d("Json Parse", myResponse);
                    Gson gson = new Gson();
                    shortInfoList = gson.fromJson(myResponse, new TypeToken<ArrayList<ShortInfo>>() {
                    }.getType());
                    for (int i = 0; i < shortInfoList.size(); i++) {
                        Log.d("Json Parse", shortInfoList.get(i).getTitle());
                        Log.d("Json Parse", String.valueOf(shortInfoList.get(i).getId()));
                    }
                    response.close();
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return shortInfoList;
    }

    public Recipe getRecipeByID(String id) throws InterruptedException {
        recipe = new Recipe();
        String url = "https://api.spoonacular.com/recipes/" + id + "/information" + apiKey + "&includeNutrition=true";
        //Set Up Http Client
        setUpHttpClient(url);
        //CountDownLatch -> Wartet bis Response komplett ist.
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //Make http Request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                recipe = new Recipe();
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String myResponse = response.body().string();
                    //shortInfoList=parseShortRecipe(myResponse);
                    Log.d("Json Parse", myResponse);
                    Gson gson = new Gson();
                    recipe = gson.fromJson(myResponse, Recipe.class);
                    Log.d("Json Parse", recipe.getTitle());
                    Log.d("Json Parse", String.valueOf(recipe.getId()));
                    Log.d("Json Parse", recipe.getImage());
                    Log.d("Json Parse", recipe.getExtendedIngredients().get(0).getName());
                    Log.d("Json Parse", String.valueOf(recipe.getExtendedIngredients().get(0).getAmount()));
                    Log.d("Json Parse", recipe.getExtendedIngredients().get(0).getUnit());
                    Log.d("Json Parse", recipe.getNutrition().getNutrients().get(0).getName());
                    Log.d("Json Parse", String.valueOf(recipe.getNutrition().getNutrients().get(0).getAmount()));
                    Log.d("Json Parse", recipe.getNutrition().getNutrients().get(0).getUnit());
                    response.close();
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return recipe;

    }

    public ArrayList<Recipe> getRandomRecipe(int amount) throws InterruptedException {
        recipeList = new ArrayList<>();
        String url = "https://api.spoonacular.com/recipes/random" + apiKey + "&number="+amount;
        //Set Up Http Client
        setUpHttpClient(url);
        //CountDownLatch -> Wartet bis Response komplett ist.
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //Make http Request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                recipe = new Recipe();
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String myResponse = response.body().string();
                    //shortInfoList=parseShortRecipe(myResponse);
                    JsonObject jo = (JsonObject) JsonParser.parseString(myResponse);
                    JsonArray jsonArr = jo.getAsJsonArray("recipes");
                    Gson gson = new Gson();
                    recipeList = gson.fromJson(jsonArr, new TypeToken<ArrayList<Recipe>>(){}.getType());
                    for (int i = 0; i < recipeList.size(); i++) {
                        Log.d("Json Parse", recipeList.get(i).getTitle());
                        Log.d("Json Parse", String.valueOf(recipeList.get(i).getId()));
                        Log.d("Json Parse", recipeList.get(i).getImage());
                        Log.d("Json Parse", String.valueOf(recipeList.get(i).getHealthScore()));
                    }



                    response.close();
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return recipeList;
    }

    private String buildURL(HashMap<String, String> general, HashMap<String, String> macronutrients, HashMap<String, String> micronutrients, HashMap<String, String> vitamins){
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : general.entrySet()) {
            if(!entry.getValue().isEmpty()&& !Objects.equals(entry.getValue(), "null")){
                result.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        for (Map.Entry<String, String> entry : macronutrients.entrySet()) {
            if(!entry.getValue().equals("-1.0")){
                result.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        for (Map.Entry<String, String> entry : micronutrients.entrySet()) {
            if(!entry.getValue().equals("-1.0")){
                result.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        for (Map.Entry<String, String> entry : vitamins.entrySet()) {
            if(!entry.getValue().equals("-1.0")){
                result.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        Log.d("search", String.valueOf(result));
        return result.toString();
    }










    public ArrayList<ShortInfo> getTestData(){

        ArrayList<ShortInfo> testdata = new ArrayList<>();
        for(int i=0; i<10; i++){
            testdata.add(new ShortInfo(i, ("Recipe "+i), "https://spoonacular.com/recipeImages/638148-312x231.jpg"));
        }
        return testdata;

    }


}



