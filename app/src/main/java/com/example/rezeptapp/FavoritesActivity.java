package com.example.rezeptapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FavoritesActivity extends AppCompatActivity {

    ImageView homeButton;
    ImageView recipiesButton;
    ImageView favoritesButton;
    ImageView searchButton;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        homeButton = findViewById(R.id.homeIcon);
        recipiesButton = findViewById(R.id.recipiesIcon);
        favoritesButton= findViewById(R.id.favoritesIcon);
        searchButton= findViewById(R.id.searchIcon);

        //navigate to HomePage
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoritesActivity.this,MainActivity.class));
            }
        });


        //navigate to SearchPage
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoritesActivity.this,SearchActivity.class));
            }
        });



        //navigate to RecipiesPage
        recipiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoritesActivity.this,RecipiesActivity.class));
            }
        });


        //navigate to FavoritesPage
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoritesActivity.this,FavoritesActivity.class));
            }
        });


    }
}