package com.example.rezeptapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    ImageView homeButton;
    ImageView recipiesButton;
    ImageView favoritesButton;
    ImageView searchButton;

    //Nur für Testzwecke
    Button goToRandomRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeButton = findViewById(R.id.homeIcon);
        recipiesButton = findViewById(R.id.recipiesIcon);
        favoritesButton= findViewById(R.id.favoritesIcon);
        searchButton= findViewById(R.id.searchIcon);

        //Nur für Testzwecke
        goToRandomRecipe = findViewById(R.id.buttonGoToRandomRecipeActivity);
        goToRandomRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RandomRecipeDemoActivity.class));
            }
        });


        //navigate to HomePage
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });


        //navigate to SearchPage
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });



        //navigate to RecipiesPage
        recipiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RecipiesActivity.class));
            }
        });


        //navigate to FavoritesPage
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FavoritesActivity.class));
            }
        });

    }

}
