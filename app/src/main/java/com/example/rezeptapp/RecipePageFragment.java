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

public class RecipePageFragment extends Fragment {
    String recipeID ="";
    Recipe recipe = new Recipe();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            recipeID = bundle.getString("foundRecipe");
            Log.d("result5", "Recipe ID: "+ recipeID);
        }
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
                  return false;
              }
          }, getViewLifecycleOwner()
        );


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_page, container, false);
    }

}