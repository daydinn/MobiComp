package com.example.rezeptapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
                + ID_COL + " INTEGER , "
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
        if(response<=-1){
            return false;
        }
        return true;
    }


}
