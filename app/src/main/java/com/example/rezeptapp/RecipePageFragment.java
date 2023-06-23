package com.example.rezeptapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RecipePageFragment extends Fragment {
    String recipeID ="";
    Recipe recipe = new Recipe();
    DBHandler dbHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            recipeID = bundle.getString("foundRecipe");
            Log.d("result5", "Recipe ID: "+ recipeID);
        }
        try {
            SearchManager searchmanager = new SearchManager();
            ArrayList<Recipe> recipeList = searchmanager.getRandomRecipe(0);
            recipe = recipeList.get(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        dbHandler = new DBHandler(getContext());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Options Menu
        requireActivity().addMenuProvider(new MenuProvider() {
              @Override
              public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                  menuInflater.inflate(R.menu.found_recipe_menu, menu);

              }

              @Override
              public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                  if (menuItem.getItemId() == R.id.foundRecipeSave) {
                      int id = recipe.getId();
                      String title = recipe.getTitle();
                      String image = recipe.getImage();
                      boolean favorite = recipe.isFavorite();

                      if(id==0){
                          Snackbar.make(getView(), "Error!\nRecipe can't be saved", Snackbar.LENGTH_SHORT).show();
                          return true;
                      }

                      if(dbHandler.addNewShortInfo(id, title, image, favorite)){
                          Snackbar.make(getView(), "Recipe saved", Snackbar.LENGTH_SHORT).show();
                      }
                      else{

                          Snackbar.make(getView(), "Error!\nRecipe can't be saved", Snackbar.LENGTH_SHORT).show();
                      }

                  }
                  return true;
              }
          }, getViewLifecycleOwner()
        );


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_page, container, false);
    }

}