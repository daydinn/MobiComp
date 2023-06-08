package com.example.rezeptapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class SearchResultsFragment extends Fragment {

    ImageButton backButton;
    TextView recipeIdTest;
    TextView recipeNameTest;
    TextView recipeCategoryTest;

    Button reloadButton;


    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    private SearchManager searchManager = new SearchManager();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);
         backButton = (ImageButton) view.findViewById(R.id.backButton);
         reloadButton = (Button) view.findViewById(R.id.reloadButton);
         recipeIdTest= (TextView) view.findViewById(R.id.recipeIdTest);
         recipeNameTest= (TextView) view.findViewById(R.id.recipeNameTest);
         recipeCategoryTest= (TextView) view.findViewById(R.id.recipeCategoryTest);
        loadFoundRecipe();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment searchFragment = new SearchFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,searchFragment).commit();            }
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

        return view;


    }

    private void loadFoundRecipe(){
        Thread thread = new Thread(){
            public void run(){
                try {

                    recipes = searchManager.getRandomRecipe();
                    //Write first recipe into activity

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Recipe recipe = recipes.get(0);
                            recipeIdTest.setText(recipe.getId());
                            recipeCategoryTest.setText(recipe.getCategory());
                            recipeNameTest.setText(recipe.getName());
                        }
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        thread.start();




    }



}