package com.example.rezeptapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME =  "recipe_app_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "myShortInfos";
    private static final String ID_COL = "id";
    private static final String TITLE_COL = "title";
    private static final String IMAGE_COL = "image";
    private static final String FAVORITE_COL = "favorite";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY UNIQUE, "
                + TITLE_COL + " TEXT,"
                + IMAGE_COL + " TEXT,"
                + FAVORITE_COL + " BOOLEAN)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

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
                    ShortInfo shortInfo = new ShortInfo(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2));
                    shortInfo.setFavorite((cursor.getInt(3)==1));
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
