package com.example.rezeptapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME =  "recipe_rhapsody_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "myRecipes";
    private static final String INDEX_COL = "indexcol";
    private static final String ID_COL = "id";
    private static final String TITLE_COL = "title";
    private static final String IMAGE_COL = "image";
    private static final String FAVORITE_COL = "favorite";


    private static final String TABLE_NAME_OFFLINE = "myOfflineRecipes";
    private static final String INDEX_COL_OFFLINE = "indexcol";
    private static final String ID_COL_OFFLINE = "id";
    private static final String JSON_RECIPE = "json";
    private static final String FAVORITE_COL_OFFLINE = "favorite";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + INDEX_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_COL + " INTEGER UNIQUE, "
                + TITLE_COL + " TEXT,"
                + IMAGE_COL + " TEXT,"
                + FAVORITE_COL + " BOOLEAN)";

        db.execSQL(query);

        String queryOff = "CREATE TABLE " + TABLE_NAME_OFFLINE + " ("
                + INDEX_COL_OFFLINE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_COL_OFFLINE + " INTEGER UNIQUE, "
                + JSON_RECIPE + " TEXT,"
                + FAVORITE_COL_OFFLINE + " BOOLEAN)";

        db.execSQL(queryOff);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_OFFLINE);
        onCreate(db);
    }
//Offline________________________________________________________________________
    /**
     *
     * Add a new offline recipe to the Database by parsing a Recipe object to Json using Gson library.
     * Returns a boolean depending on whether the Database insert was successful or not.
     * @Author Rene Wentzel
     * @param id The id of the recipe
     * @param recipe The Recipe object that shall be saved.
     * @param favorite The favorite status
     * @return Returns true when insertion was successful. Returns false when insertion failed.
     */
    public boolean addNewOfflineRecipe(int id, Recipe recipe,boolean favorite){
        // Open Database in Write Mode
        SQLiteDatabase db = this.getWritableDatabase();

        //Create new Conten values
        ContentValues values = new ContentValues();

        //Serialize the Recipe object with Gson
        Gson gson = new Gson();
        String recipeString = gson.toJson(recipe);

        //Set Content
        values.put(ID_COL_OFFLINE, id);
        values.put(JSON_RECIPE, recipeString);
        values.put(FAVORITE_COL_OFFLINE, favorite);

        //Insert Content into Table
        long response = db.insert(TABLE_NAME_OFFLINE, null, values);
        db.close();
        return response > -1;
    }
    /**
     * Checks by id if a given dataset already exists in the Offline Recipe database.
     * @Author Rene Wentzel
     * @param id The id that get checked
     * @return Returns true if data already exists. Returns false if data does not exist.
     */
    public boolean checkOfflineDataExists(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_OFFLINE + " WHERE id = " + id, null);

        boolean recordExists = (cursor.getCount() > 0);

        cursor.close();
        db.close();

        return recordExists;
    }

    /**
     * Removes the data set with the given id from the offline database.
     * Returns a boolean depending on whether the update was successful or not.
     * @Author Rene Wentzel
     * @param id The id of the recipe
     * @return Returns true when removal was successful. Returns false when removal failed.
     */
    public boolean deleteOfflineRecipe(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        //delete Data by id
        long response = db.delete(TABLE_NAME_OFFLINE, "id=?", new String[]{String.valueOf(id)});
        db.close();
        return response > -1;
    }

    /**
     * Read all data sets in the database and parse them into ShortInfo Objects.
     * Returns an list of all ShortInfo Objects
     * @Author Rene Wentzel
     * @return Returns a ArrayList of ShortInfo Objects (ArrayList<ShortInfo>)
     */
    public ArrayList<ShortInfo> getAllOfflineRecipes(){

        //Open Database in read mode
        SQLiteDatabase db = this.getReadableDatabase();

        Log.d("gson","getOfflineRecipes start");
        //Get all Offline Recipes
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_OFFLINE, null);

        ArrayList<ShortInfo> shortInfoList = new ArrayList<>();

        Gson gson = new Gson();
        // Add Data to Recipe List
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = gson.fromJson(cursor.getString(2), Recipe.class);
                ShortInfo shortInfo = new ShortInfo(recipe.getId(), recipe.getTitle(), recipe.getImage());
                shortInfo.setFavorite(recipe.isFavorite());
                shortInfoList.add(shortInfo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return shortInfoList;
    }

    /**
     * Read all data sets in the database for offline recipes where Favorite column is true and parse them into Recipe Objects.
     * Returns an list of all ShortInfo Objects
     * @Author Rene Wentzel
     * @return Returns a ArrayList of Recipe Objects (ArrayList<Recipe>)
     */
    public ArrayList<ShortInfo> getAllFavoriteOfflineRecipes(){

        //Open Database in read mode
        SQLiteDatabase db = this.getReadableDatabase();

        //Get all Recipes
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_OFFLINE + " WHERE " + FAVORITE_COL_OFFLINE + " = true", null);

        ArrayList<ShortInfo> shortInfoList = new ArrayList<>();

        Gson gson = new Gson();
        // Add Data to Recipe List
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = gson.fromJson(cursor.getString(2), Recipe.class);
                ShortInfo shortInfo = new ShortInfo(recipe.getId(), recipe.getTitle(), recipe.getImage());
                shortInfo.setFavorite(recipe.isFavorite());
                shortInfoList.add(shortInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return shortInfoList;
    }


    /**
     * Read the latest data sets added to the database for offline recipes and parse them into Recipe Objects.
     * The amount of data sets getting back can be set via parameter.
     * Returns an list of all Recipe Objects
     * @Author Rene Wentzel
     * @param amount Tha amount of data sets given back.
     * @return Returns a ArrayList of Recipe Objects (ArrayList<Recipe>)
     */
    public ArrayList<ShortInfo> getLatestOfflineRecipes(int amount){

        //Open Database in read mode
        SQLiteDatabase db = this.getReadableDatabase();

        //Get all Recipes
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_OFFLINE + " ORDER BY indexcol DESC LIMIT " + amount, null);

        ArrayList<ShortInfo> shortInfoList = new ArrayList<>();

        Gson gson = new Gson();
        // Add Data to Recipe List
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = gson.fromJson(cursor.getString(2), Recipe.class);
                ShortInfo shortInfo = new ShortInfo(recipe.getId(), recipe.getTitle(), recipe.getImage());
                shortInfo.setFavorite(recipe.isFavorite());
                shortInfoList.add(shortInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return shortInfoList;
    }

    /**
     * Returns the recipe with the given id, if exists.
     * Returns an empty recipe, if no data found.
     * @Author Rene Wentzel
     * @param id The id of the searched recipe
     * @return Returns a Recipe object.
     */
    public Recipe getOfflineRecipeByID(String id){
        //Open Database in read mode
        SQLiteDatabase db = this.getReadableDatabase();

        //Get Recipe
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_OFFLINE + " WHERE "+ID_COL_OFFLINE+" = "+id, null);

        Recipe recipe = new Recipe();

        // Add Data to Recipe List
        if (cursor.moveToFirst()) {
            do {
                Gson gson = new Gson();
                recipe = gson.fromJson(cursor.getString(2), Recipe.class);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recipe;
    }


    /**
     * Updates the Favorite column of a data set and the favorite value of it's Recipe object.
     * Returns a boolean depending on whether the update was successful or not.
     * Uses the getOfflineRecipeByID methode to get the recipe.
     * @Author Rene Wentzel
     * @param id The id of the recipe
     * @param favorite The favorite status
     * @return Returns true when update was successful. Returns false when update failed.
     */
    public boolean updateOfflineRecipeFavorite(int id, boolean favorite){
        Recipe recipe = getOfflineRecipeByID(String.valueOf(id));
        recipe.setFavorite(favorite);

        //Serialize the Recipe object with Gson
        Gson gson = new Gson();
        String recipeString = gson.toJson(recipe);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID_COL_OFFLINE, id);
        values.put(JSON_RECIPE, recipeString);
        values.put(FAVORITE_COL_OFFLINE, favorite);

        // Updating Data by id
        long response = db.update(TABLE_NAME_OFFLINE, values, "id=?", new String[]{String.valueOf(id)});
        db.close();
        return response > -1;
    }

    /**
     * For offline Recipes, checks if the data set's favorite value of the given id is true or false.
     * @param id The id of the searched data set
     * @return Returns the favorite valure as a boolean
     */
    public boolean isOfflineDataFavorite(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID_COL_OFFLINE, id);

        // Updating Data by id
        Cursor cursor = db.rawQuery("SELECT "+FAVORITE_COL_OFFLINE+" FROM " + TABLE_NAME_OFFLINE+" WHERE id = "+id, null);
        boolean result = false;
        if (cursor.moveToFirst()) {
            result = (cursor.getInt(0)==1);
        }
        cursor.close();
        db.close();
        return result;
    }



//Online_____________________________________________________________
    /**
     *
     * Add a new recipe to the Database.
     * Returns a boolean depending on whether the Database insert was successful or not.
     * @Author Rene Wentzel
     * @param id The id of the recipe
     * @param title The name of the recipe
     * @param image The URL to the image of the recipe
     * @param favorite The favorite status
     * @return Returns true when insertion was successful. Returns false when insertion failed.
     */
    public boolean addNewShortInfo(int id, String title, String image, boolean favorite){
        // Open Database in Write Mode
        SQLiteDatabase db = this.getWritableDatabase();

        //Create new Conten values
        ContentValues values = new ContentValues();

        //Set Content
        values.put(ID_COL, id);
        values.put(TITLE_COL, title);
        values.put(IMAGE_COL, image);
        values.put(FAVORITE_COL, favorite);

        //Insert Content into Table
        long response = db.insert(TABLE_NAME, null, values);
        db.close();
        return response > -1;
    }

    /**
     * Checks by id if a given dataset already exists in the database.
     * @Author Rene Wentzel
     * @param id The id that get checked
     * @return Returns true if data already exists. Returns false if data does not exist.
     */
    public boolean checkDataExists(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id, null);

        boolean recordExists = (cursor.getCount() > 0);

        cursor.close();
        db.close();

        return recordExists;
    }

    /**
     * Read all data sets in the database and parse them into ShortInfo Objects.
     * Returns an list of all ShortInfo Objects
     * @Author Rene Wentzel
     * @return Returns a ArrayList of ShortInfo Objects (ArrayList<ShortInfo>)
     */
    public ArrayList<ShortInfo> getAllShortInfo(){

        //Open Database in read mode
        SQLiteDatabase db = this.getReadableDatabase();

        //Get all ShortInfos
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<ShortInfo> shortInfoList = new ArrayList<>();

            // Add Data to shortInfo List
            if (cursor.moveToFirst()) {
                do {
                    ShortInfo shortInfo = new ShortInfo(cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getString(3));
                    shortInfo.setFavorite((cursor.getInt(4)==1));
                    shortInfoList.add(shortInfo);
                } while (cursor.moveToNext());
            }
        cursor.close();
        return shortInfoList;
    }

    /**
     * Read all data sets in the database where Favorite column is true and parse them into ShortInfo Objects.
     * Returns an list of all ShortInfo Objects
     * @Author Rene Wentzel
     * @return Returns a ArrayList of ShortInfo Objects (ArrayList<ShortInfo>)
     */
    public ArrayList<ShortInfo> getAllFavoriteShortInfo(){

        //Open Database in read mode
        SQLiteDatabase db = this.getReadableDatabase();

        //Get all ShortInfos
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + FAVORITE_COL + " = true", null);

        ArrayList<ShortInfo> shortInfoList = new ArrayList<>();

        // Add Data to shortInfo List
        if (cursor.moveToFirst()) {
            do {
                ShortInfo shortInfo = new ShortInfo(cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3));
                shortInfo.setFavorite((cursor.getInt(4)==1));
                shortInfoList.add(shortInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return shortInfoList;
    }

    /**
     * Read the latest data sets added to the database and parse them into ShortInfo Objects.
     * The amount of data sets getting back can be set via parameter.
     * Returns an list of all ShortInfo Objects
     * @Author Rene Wentzel
     * @param amount Tha amount of data sets given back.
     * @return Returns a ArrayList of ShortInfo Objects (ArrayList<ShortInfo>)
     */
    public ArrayList<ShortInfo> getLatestShortInfos(int amount){

        //Open Database in read mode
        SQLiteDatabase db = this.getReadableDatabase();

        //Get all ShortInfos
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY indexcol DESC LIMIT " + amount, null);

        ArrayList<ShortInfo> shortInfoList = new ArrayList<>();

        // Add Data to shortInfo List
        if (cursor.moveToFirst()) {
            do {
                ShortInfo shortInfo = new ShortInfo(cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3));
                shortInfo.setFavorite((cursor.getInt(4)==1));
                shortInfoList.add(shortInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return shortInfoList;
    }

    /**
     * Updates an existing data set with given parameters.
     * Returns a boolean depending on whether the update was successful or not.
     * @Author Rene Wentzel
     * @param id The id of the recipe
     * @param title The name of the recipe
     * @param image The URL to the image of the recipe
     * @param favorite The favorite status
     * @return Returns true when update was successful. Returns false when update failed.
     */
    public boolean updateShortInfo(int id, String title, String image, boolean favorite){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID_COL, id);
        values.put(TITLE_COL, title);
        values.put(IMAGE_COL, image);
        values.put(FAVORITE_COL, favorite);

        // Updating Data by id
        long response = db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(id)});
        db.close();
        return response > -1;
    }

    /**
     * Updates the Favorite column of a data set.
     * Returns a boolean depending on whether the update was successful or not.
     * @Author Rene Wentzel
     * @param id The id of the recipe
     * @param favorite The favorite status
     * @return Returns true when update was successful. Returns false when update failed.
     */
    public boolean updateShortInfoFavorite(int id, boolean favorite){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID_COL, id);
        values.put(FAVORITE_COL, favorite);

        // Updating Data by id
        long response = db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(id)});
        db.close();
        return response > -1;
    }

    /**
     * Checks if the data set's favorite value of the given id is true or false.
     * @param id The id of the searched data set
     * @return Returns the favorite valure as a boolean
     */
    public boolean isShortInfoFavorite(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID_COL, id);

        // Updating Data by id
        Cursor cursor = db.rawQuery("SELECT "+FAVORITE_COL+" FROM " + TABLE_NAME+" WHERE id = "+id, null);
        boolean result = false;
        if (cursor.moveToFirst()) {
            result = (cursor.getInt(0)==1);
        }
        cursor.close();
        db.close();
        return result;
    }

    /**
     * Removes the data set with the given id.
     * Returns a boolean depending on whether the update was successful or not.
     * @Author Rene Wentzel
     * @param id The id of the recipe
     * @return Returns true when removal was successful. Returns false when removal failed.
     */
    public boolean deleteShortInfo(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        //delete Data by id
        long response = db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        db.close();
        return response > -1;
    }


}
