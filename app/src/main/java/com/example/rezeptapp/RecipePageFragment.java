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
                  menuInflater.inflate(R.menu.recipe_menu, menu);
                  //menu.findItem(R.id.favoriteItem).setVisible(false);
              }
              @Override
              public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                  if (menuItem.getItemId() == R.id.recipeSaveItem) {
                      int id = recipe.getId();
                      String title = recipe.getTitle();
                      String image = recipe.getImage();
                      boolean favorite = recipe.isFavorite();

                      if(id==0){
                          Snackbar.make(getView(), "Error!\nRecipe can't be saved", Snackbar.LENGTH_SHORT).show();
                          return true;
                      }

                      //if(dbHandler.addNewShortInfo(id, title, image, favorite)){
                      if(dbHandler.addNewShortInfo(5, "Title", "image.url", false)){
                          Snackbar.make(getView(), "Recipe saved", Snackbar.LENGTH_SHORT).show();
                      }
                      else{

                          Snackbar.make(getView(), "Error!\nRecipe can't be saved", Snackbar.LENGTH_SHORT).show();
                      }

                  }
                  else if(menuItem.getItemId() == R.id.favoriteItem){

                      //menuItem.setVisible(false);
                      Log.d("load5","Looaaadd---------");
                      ArrayList<ShortInfo> shortInfoList = dbHandler.getAllShortInfo();
                      Log.d("load5", String.valueOf(shortInfoList.size()));
                      for(int i=0; i<shortInfoList.size();i++){
                          Log.d("load5", String.valueOf(shortInfoList.get(i).getId()));
                          Log.d("load5",shortInfoList.get(i).getTitle());
                          Log.d("load5",shortInfoList.get(i).getImage());
                          Log.d("load5", String.valueOf(shortInfoList.get(i).isFavorite()));
                      }
                  }
                  else if(menuItem.getItemId() == R.id.recipeUpdateItem){
                      if(dbHandler.updateShortInfo(5,"newTitle", "newImage.url", true)){
                          Snackbar.make(getView(), "Recipe Updated", Snackbar.LENGTH_SHORT).show();
                      }
                      else{
                          Snackbar.make(getView(), "Error!\nRecipe not updated", Snackbar.LENGTH_SHORT).show();
                      }
                  }
                  else if(menuItem.getItemId() == R.id.recipeDeleteItem){
                      if(dbHandler.deleteShortInfo(5)){
                          Snackbar.make(getView(), "Recipe Deleted", Snackbar.LENGTH_SHORT).show();
                      }
                      else{
                          Snackbar.make(getView(), "Error!\nRecipe not deleted", Snackbar.LENGTH_SHORT).show();
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