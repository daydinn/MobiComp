package com.example.rezeptapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class RandomRecipeDemoActivity extends AppCompatActivity {

    private TextView textviewResult;
    private TextView textviewName;
    private TextView textviewCategory;
    private TextView textviewArea;
    private TextView textviewMeasurement;
    private TextView textviewIngredient;
    private TextView textviewInstructions;
    private ImageView imageView01;
    private TextView textviewYoutube;
    private TextView textviewSource;
    private Button newRecipeButton;

    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
    private RecipeManager recipeManager = new RecipeManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_recipe_demo);
        textviewResult = findViewById(R.id.textViewName);
        imageView01 = findViewById(R.id.imageView01);
        textviewName = findViewById(R.id.textViewName);
        textviewCategory = findViewById(R.id.textViewCategory);
        textviewArea = findViewById(R.id.textViewArea);
        textviewMeasurement = findViewById(R.id.textViewMeasurement);
        textviewIngredient = findViewById(R.id.textViewIngredient);
        textviewInstructions = findViewById(R.id.textViewInstructions);
        textviewYoutube = findViewById(R.id.textViewYoutube);
        textviewSource = findViewById(R.id.textViewSource);
        newRecipeButton = findViewById(R.id.buttonRandomRecipe);

        loadRandomRecipe();

        newRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipes = new ArrayList<Recipe>();
                textviewIngredient.setText("");
                textviewMeasurement.setText("");
                loadRandomRecipe();
            }
        });
    }


    private void loadRandomRecipe(){
        try {
            recipes = recipeManager.getRandomRecipe();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Log.d("number","1:"+recipes.size());

        //Write first recipe into activity
        RandomRecipeDemoActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Recipe recipe = recipes.get(0);
                textviewName.setText(recipe.getName());
                textviewCategory.setText(recipe.getCategory());
                textviewArea.setText(recipe.getArea());
                textviewInstructions.setText(recipe.getInstruction());
                if (!recipe.getImageURL().isEmpty()) {
                    Picasso.get().load(recipe.getImageURL()).into(imageView01);
                }
                if (!recipe.getYoutubeURL().isEmpty()) {
                    textviewYoutube.setText(recipe.getYoutubeURL());
                }
                if (!recipe.getSourceURL().isEmpty()) {
                    textviewSource.setText(recipe.getSourceURL());
                }

                for (int i = 0; i < recipe.getIngredient().size(); i++) {
                    textviewIngredient.append(recipe.getIngredient().get(i) + "\n");
                    textviewMeasurement.append(recipe.getMeasurement().get(i) + "\n");
                }
            }
        });
    }
}