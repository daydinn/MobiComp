package com.example.rezeptapp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
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
    //private String apiKey = "?apiKey=45f6a0cce7b04513bab4ecef0cb5a2d7";
    //private String apiKey = "?apiKey=5";

    /**
     * Resets the OkHttpClient and starts a request to the given URL.
     * @Author Rene Wentzel
     * @param url URL of the website that should be called.
     */
    public void setUpHttpClient(String url) {
        //http request with okhttp
        //Set up http client
        client = new OkHttpClient();
        request = new Request.Builder().url(url).build();
    }

    /**
     * Searches for recipes only by a recipe name.
     * internally uses the searchRecipes methode filled with empty arguments except for the recipe name.
     * @Author Rene Wentzel
     * @param name The name that recipes should be searched for
     * @return When request was successful, returns an ArrayList with the search results. When request was not successful, returns an empty ArrayList.
     * @throws InterruptedException Waits for response of the API. Throws InterruptesException when response takes too long.
     */
    public ArrayList<ShortInfo> quickSearch(String name) throws InterruptedException {
        shortInfoList = new ArrayList<>();
        HashMap<String, String> search = new HashMap<>();
        search.put("query", name);
        HashMap<String, String> nothing = new HashMap<>();
        shortInfoList = searchRecipes(search,nothing, nothing, nothing);
        return shortInfoList;
    }


    /**
     * Connects to Spoonacular API and gives back a list of recipes that fit the filters given by HashMaps.
     * Each Hashmap has a certain set of items that represent one search filter.
     * See createSearchHashmaps() methode in SearchFragment.java to get a full list of HashMap items.
     * Returns an ArrayList of ShortInfo objects (ArrayList<ShortInfo>).
     * @Author Rene Wentzel
     * @param general HashMap of general search filters
     * @param macronutrients HashMap of macro nutrients search filters
     * @param micronutrients HashMap of micro nutrients search filters
     * @param vitamins Hashmap of vitamin search filters
     * @return When request was successful, returns an ArrayList with the search results. When request was not successful, returns an empty ArrayList.
     * @throws InterruptedException Waits for response of the API. Throws InterruptesException when response takes too long.
     */
    public ArrayList<ShortInfo> searchRecipes(HashMap<String, String> general, HashMap<String, String> macronutrients, HashMap<String, String> micronutrients, HashMap<String, String> vitamins) throws InterruptedException {
        shortInfoList = new ArrayList<>();
        String url = "https://api.spoonacular.com/recipes/complexSearch"+apiKey+ buildURL(general, macronutrients, micronutrients, vitamins) + "&instructionsRequired=true";
        Log.d("search", url);
        //Set Up Http Client
        setUpHttpClient(url);
        //CountDownLatch -> Wartet bis Response komplett ist.
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //Make http Request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                shortInfoList = new ArrayList<>();
                Log.d("Error", "Request failed");
                e.printStackTrace();
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String myResponse = response.body().string();
                    Log.d("Json Parse", myResponse);
                    JsonObject jo = (JsonObject) JsonParser.parseString(myResponse);
                    Gson gson = new Gson();
                    shortInfoList = gson.fromJson(jo.getAsJsonArray("results"), new TypeToken<ArrayList<ShortInfo>>(){}.getType());
                    for (int i = 0; i < shortInfoList.size(); i++) {
                        Log.d("Json Parse", shortInfoList.get(i).getTitle());
                        Log.d("Json Parse", String.valueOf(shortInfoList.get(i).getId()));
                        Log.d("Json Parse", shortInfoList.get(i).getImage());
                    }
                    response.close();
                    countDownLatch.countDown();
                }
                else{
                    shortInfoList = new ArrayList<>();
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return shortInfoList;
    }

    /**
     * Connects to the Spoonacular API and gives back a list of recipes similar to the one of the given id.
     * @Author Rene Wentzel
     * @param id The id of the recipe where similar recipes should be searched for
     * @param amount The amount of similar recipes given back.
     * @return When request was successful, returns an ArrayList with the recipes. When request was not successful, returns an empty ArrayList.
     * @throws InterruptedException Waits for response of the API. Throws InterruptesException when response takes too long.
     */
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
                else{
                    recipe = new Recipe();
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return shortInfoList;
    }

    /**
     * Connects to the Spoonacular API and returns the recipe of the given id.
     * @Author Rene Wentzel
     * @param id The id of the recipe the API should return
     * @return Returns a Recipe object with the searched recipe when request was successful. Returns an empty Recipe Object when request failed.
     * @throws InterruptedException Waits for response of the API. Throws InterruptesException when response takes too long.
     */
    public Recipe getRecipeByID(String id) throws InterruptedException {
        recipe = new Recipe();
        String url = "https://api.spoonacular.com/recipes/" + id + "/information" + apiKey + "&includeNutrition=true";
        Log.d("search", url);
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
                    response.close();
                    countDownLatch.countDown();
                }
                else{
                    recipe = new Recipe();
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return recipe;

    }

    /**
     * Connects to the Spoonacular API and gives back a list of random recipes.
     * Returns an ArrayList of ShortInfos (ArrayList<ShortInfo>).
     * @Author Rene Wentzel
     * @param amount The amount of random recipes that should be returned
     * @return On success returns an ArrayList of ShortInfos. On failure returns an empty ArrayList
     * @throws InterruptedException Waits for response of the API. Throws InterruptesException when response takes too long.
     */
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
                    JsonObject jo = (JsonObject) JsonParser.parseString(myResponse);
                    Gson gson = new Gson();
                    recipeList = gson.fromJson(jo.getAsJsonArray("recipes"), new TypeToken<ArrayList<Recipe>>(){}.getType());
                    /*for (int i = 0; i < recipeList.size(); i++) {
                        Log.d("Json Parse", recipeList.get(i).getTitle());
                        Log.d("Json Parse", String.valueOf(recipeList.get(i).getId()));
                        Log.d("Json Parse", recipeList.get(i).getImage());
                        Log.d("Json Parse", String.valueOf(recipeList.get(i).getHealthScore()));
                    }*/
                    response.close();
                    countDownLatch.countDown();
                }
                else{
                    recipe = new Recipe();
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        return recipeList;
    }

    /**
     * Takes values of given HashMaps and turnes them into a valid URL attachment for a complex search of the Spoonacular API.
     * Filters out empty values.
     * The HashMaps must have a certain structure (see createSearchHashmaps() methode in SearchFragment.java).
     * @Author Rene Wentzel
     * @param general HashMap of general search filters
     * @param macronutrients HashMap of macro nutrients search filters
     * @param micronutrients HashMap of micro nutrients search filters
     * @param vitamins Hashmap of vitamin search filters
     * @return Returns a String that can be attached to the URL for a complex search of the Spoonacular API.
     */
    private String buildURL(HashMap<String, String> general, HashMap<String, String> macronutrients, HashMap<String, String> micronutrients, HashMap<String, String> vitamins){
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : general.entrySet()) {
            if(!entry.getValue().isEmpty()&& !Objects.equals(entry.getValue(), "") && !Objects.equals(entry.getValue(), "0")){
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


    /**
     * Creates an ArrayList of ShortInfo objects (ArrayList<ShortInfo>) for test purpose.
     * Author Rene Wentzel
     * @return Returns an ArrayList of created ShortInfo objects.
     */
    public ArrayList<ShortInfo> getTestData(){

        ArrayList<ShortInfo> testdata = new ArrayList<>();
        for(int i=0; i<10; i++){
            testdata.add(new ShortInfo(i, ("Recipe "+i), "https://spoonacular.com/recipeImages/638148-312x231.jpg"));
        }
        return testdata;

    }


}



