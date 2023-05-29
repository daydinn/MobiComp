package com.example.rezeptapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {


    ImageButton backButton;
    TextView recipeIdTest;
    TextView recipeNameTest;
    TextView recipeCategoryTest;

    Button reloadButton;

    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    private SearchManager searchManager = new SearchManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        backButton = findViewById(R.id.backButton);
        recipeIdTest = findViewById(R.id.RecipeIdTest);
        recipeNameTest = findViewById(R.id.RecipeNameTest);
        recipeCategoryTest = findViewById(R.id.RecipeCategoryTest);

        reloadButton = findViewById(R.id.reloadButton);
        loadFoundRecipe();



        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(SearchResultsActivity.this, SearchActivity.class));
            }

        });



        reloadButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    /*
                    recipeIdTest.setText(recipeManager.getRandomRecipe().get(0).getId());
                    recipeNameTest.setText(recipeManager.getRandomRecipe().get(0).getName());
                    recipeCategoryTest.setText(recipeManager.getRandomRecipe().get(0).getCategory());
 */
                    loadFoundRecipe();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }
        });



    }

    private void loadFoundRecipe(){
        try {
            recipes = searchManager.getRandomRecipe();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Write first recipe into activity
                Recipe recipe = recipes.get(0);
                recipeIdTest.setText(recipe.getId());
                recipeCategoryTest.setText(recipe.getCategory());
                recipeNameTest.setText(recipe.getName());

    }
}

