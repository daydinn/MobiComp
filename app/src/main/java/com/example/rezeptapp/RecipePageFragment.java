package com.example.rezeptapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class RecipePageFragment extends Fragment {

    ImageButton backButton;
    String recipeID ="";
    Recipe recipe = new Recipe();
    DBHandler dbHandler;
    TextView recipeTitle;
    ImageView recipeImage;
    RelativeLayout ingredientLayout;
    TextView instructions;

    //Infos
    TextView cookingTime;
    TextView servings;
    ImageView cuisineIcon;
    TextView cuisines;
    ImageView vegetarian;
    ImageView vegan;
    ImageView glutenFree;
    ImageView dairyFree;
    ImageView lowFODMap;
    ImageView healthScoreIcon;
    TextView healthScore;
    ImageView sustainable;
    LinearLayout cuisineDishLayout;
    LinearLayout macroLayout;
    LinearLayout microLayout;
    LinearLayout vitaminLayout;
    ImageView macroView;
    ImageView microView;
    ImageView vitaminView;
    boolean isRecipeSaved=false;

    RecyclerView macroRecyclerView;
    RecyclerView microRecyclerView;
    RecyclerView vitaminRecyclerView;
    RecyclerView ingredientRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Gets data from previous page. If data exists, writes into recipeID.                       Rene Wentzel
        Bundle bundle = getArguments();
        if(bundle!=null){
            recipeID = bundle.getString("foundRecipe");
        }

        //Starts request to the API to get recipe data of recipeID.                                 Rene Wenztel
        try {
            SearchManager searchmanager = new SearchManager();
            recipe = searchmanager.getRecipeByID(recipeID);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Creates a new dbHandler. Ask database if recipe of given recipeID already exists          Rene Wentzel
        dbHandler = new DBHandler(getContext());
        isRecipeSaved = dbHandler.checkDataExists(recipeID);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_page, container, false);
        //Set Optionbar Title
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(recipe.getTitle());
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);

        recipeTitle = view.findViewById(R.id.RecipeTitleTextView);
        recipeImage = view.findViewById(R.id.bigRecipeImage);
        ingredientLayout = view.findViewById(R.id.RecipeIngredientsLayout);
        instructions = view.findViewById(R.id.RecipeInstruction);

        cookingTime = view.findViewById(R.id.ReadyTimeTextView);
        servings = view.findViewById(R.id.ServingTextView);
        cuisineDishLayout = view.findViewById(R.id.cuisineDishLayout);
        cuisines = view.findViewById(R.id.CuisineTextView);
        cuisineIcon = view.findViewById(R.id.CuisineIcon);
        vegetarian = view.findViewById(R.id.VegetarianIcon);
        vegan = view.findViewById(R.id.VeganIcon);
        glutenFree = view.findViewById(R.id.GlutenFreeIcon);
        dairyFree = view.findViewById(R.id.DairyFreeIcon);
        lowFODMap = view.findViewById(R.id.LowFODMapIcon);
        healthScoreIcon = view.findViewById(R.id.HealthScoreIcon);
        healthScore = view.findViewById(R.id.HealthScoreTextView);
        sustainable = view.findViewById(R.id.SustainableIcon);
        macroLayout = view.findViewById(R.id.RecipeMacroLayout);
        microLayout = view.findViewById(R.id.RecipeMicroLayout);
        vitaminLayout = view.findViewById(R.id.RecipeVitaminLayout);
        macroView = view.findViewById(R.id.RecipeMacroButton);
        microView = view.findViewById(R.id.RecipeMicroButton);
        vitaminView = view.findViewById(R.id.RecipeVitaminButton);

        macroRecyclerView = view.findViewById(R.id.RecipeMacroRecycler);
        microRecyclerView = view.findViewById(R.id.RecipeMicroRecycler);
        vitaminRecyclerView = view.findViewById(R.id.RecipeVitaminRecycler);
        ingredientRecyclerView = view.findViewById(R.id.RecipeIngredientRecycler);

        view.post(new Runnable() {
            @Override
            public void run() {

                recipeTitle.setText(recipe.getTitle());
                Picasso.get().load(recipe.getImage()).into(recipeImage);
                //setUpIngredients();
                //setUpNutrients();
                macroLayout.setVisibility(View.GONE);
                microLayout.setVisibility(View.GONE);
                vitaminLayout.setVisibility(View.GONE);
                try{
                    instructions.setText(Html.fromHtml(recipe.getInstructions(), HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST));
                }
                catch (Exception e){

                    Toast.makeText(getContext(),"Unable to load Recipe.\nPlease reload Page.",Toast.LENGTH_SHORT).show();
                }

                //Setting up misc information
                cookingTime.setText(recipe.getReadyInMinutes()+" minutes");
                servings.setText(recipe.getServings()+" portions");
                if(recipe.getCuisines().size()!=0){
                    cuisineIcon.setAlpha(1f);
                    for(int i=0; i<recipe.getCuisines().size();i++){
                        if(i!=0){
                            cuisines.setText(cuisines.getText()+", ");
                        }
                        cuisines.setText(cuisines.getText()+recipe.getCuisines().get(i));
                    }
                }

                if(recipe.isVegetarian())
                    vegetarian.setAlpha(1f);
                if(recipe.isVegan())
                    vegan.setAlpha(1f);
                if(recipe.isGlutenFree())
                    glutenFree.setAlpha(1f);
                if(recipe.isDairyFree())
                    dairyFree.setAlpha(1f);
                if(recipe.isLowFodmap())
                    lowFODMap.setAlpha(1f);
                if(recipe.getHealthScore()!=0){
                    healthScoreIcon.setAlpha(1f);
                    healthScore.setText(String.valueOf(recipe.getHealthScore()));
                }
                if(recipe.isSustainable())
                    sustainable.setAlpha(1f);

                //Setting up nutrients and ingredients
                SR_RecyclerViewAdapter_Recipe_Nutrients myAdapter = new SR_RecyclerViewAdapter_Recipe_Nutrients(getContext(), recipe.getMacroNutrients());
                macroRecyclerView.setAdapter(myAdapter);
                macroRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                myAdapter = new SR_RecyclerViewAdapter_Recipe_Nutrients(getContext(), recipe.getMicroNutrients());
                microRecyclerView.setAdapter(myAdapter);
                microRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                myAdapter = new SR_RecyclerViewAdapter_Recipe_Nutrients(getContext(), recipe.getVitamins());
                vitaminRecyclerView.setAdapter(myAdapter);
                vitaminRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                SR_RecyclerViewAdapter_Recipe_Ingredients ingredientsAdapter = new SR_RecyclerViewAdapter_Recipe_Ingredients(getContext(), recipe.getIngredientList());
                ingredientRecyclerView.setAdapter(ingredientsAdapter);
                ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                //ingredientRecyclerView.setNestedScrollingEnabled(false);

                /**
                 * Sets up an Option Menu in the top ActionBar.
                 * Includes saving/deleting a recipe to the local storage and setting/removing saved recipes to/from favorite.
                 * To do: Erweitern sobald fertig
                 * @Author Rene Wentzel
                 */
                requireActivity().addMenuProvider(new MenuProvider() {
                  @Override
                  public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                      menu.clear();
                      menuInflater.inflate(R.menu.recipe_menu, menu);
                      isRecipeSaved = dbHandler.checkDataExists(recipeID);
                      recipe.setFavorite(dbHandler.isShortInfoFavorite(recipe.getId()));
                      if(isRecipeSaved){
                          menu.findItem(R.id.favoriteItem).setVisible(true);
                          menu.findItem(R.id.recipeSaveItem).setVisible(false);
                          menu.findItem(R.id.recipeDeleteItem).setVisible(true);
                      }
                      else{
                          menu.findItem(R.id.favoriteItem).setVisible(false);
                          menu.findItem(R.id.recipeSaveItem).setVisible(true);
                          menu.findItem(R.id.recipeDeleteItem).setVisible(false);
                      }

                      if(recipe.isFavorite()){
                          menu.findItem(R.id.favoriteItem).setIcon(R.drawable.favorite_on);
                      }
                      else{
                          menu.findItem(R.id.favoriteItem).setIcon(R.drawable.favorite_off);
                      }
                  }
                  @Override
                  public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                      //Save Button
                      if (menuItem.getItemId() == R.id.recipeSaveItem) {

                          if(recipe.getId()==0){
                              Snackbar.make(getView(), "Error!\nRecipe can't be saved", Snackbar.LENGTH_SHORT).show();
                              return true;
                          }

                          if(dbHandler.addNewShortInfo(recipe.getId(), recipe.getTitle(), recipe.getImage(), recipe.isFavorite())){
                              Snackbar.make(getView(), "Recipe saved", Snackbar.LENGTH_SHORT).show();
                          }
                          else{

                              Snackbar.make(getView(), "Error!\nRecipe can't be saved", Snackbar.LENGTH_SHORT).show();
                          }
                          requireActivity().invalidateOptionsMenu();
                      }
                      //Favorite Icon
                      else if(menuItem.getItemId() == R.id.favoriteItem){
                          recipe.setFavorite(!recipe.isFavorite());

                          if(dbHandler.updateShortInfoFavorite(recipe.getId(), recipe.isFavorite())){

                              if(recipe.isFavorite()){
                                  menuItem.setIcon(R.drawable.favorite_on);
                                  Snackbar.make(getView(), "Favorite Set", Snackbar.LENGTH_SHORT).show();
                              }
                              else{
                                  menuItem.setIcon(R.drawable.favorite_off);
                                  Snackbar.make(getView(), "Favorite Removed", Snackbar.LENGTH_SHORT).show();
                              }
                              ArrayList<ShortInfo> rep = dbHandler.getAllShortInfo();
                              for(int i=0; i<rep.size();i++){
                                  Log.d("check5", rep.get(i).getTitle());
                                  Log.d("check5", String.valueOf(rep.get(i).isFavorite()));
                              }

                          }
                          requireActivity().invalidateOptionsMenu();
                      }
                      //Delete Button
                      else if(menuItem.getItemId() == R.id.recipeDeleteItem){
                          if(dbHandler.deleteShortInfo(recipe.getId())){
                              Snackbar.make(getView(), "Recipe Deleted", Snackbar.LENGTH_SHORT).show();
                          }
                          else{
                              Snackbar.make(getView(), "Error!\nRecipe not deleted", Snackbar.LENGTH_SHORT).show();
                          }
                          requireActivity().invalidateOptionsMenu();
                      }
                      //Share Button5062
                      else if(menuItem.getItemId() == R.id.recipeShareItem){
                          if(recipe.getSourceUrl().isEmpty()){
                              Snackbar.make(getView(), "Error!\nIt's not possible to share this recipe.", Snackbar.LENGTH_SHORT).show();
                          }
                          else{
                              Intent sendIntent = new Intent();
                              sendIntent.setAction(Intent.ACTION_SEND);
                              sendIntent.putExtra(Intent.EXTRA_TEXT, recipe.getSourceUrl());
                              sendIntent.setType("text/plain");
                              Intent shareIntent = Intent.createChooser(sendIntent, null);
                              startActivity(shareIntent);
                          }


                      }
                      return true;
                  }
              }, getViewLifecycleOwner()
                );
            }
        });

        macroView.setOnClickListener(v -> {
            if(macroLayout.getVisibility()==View.GONE){
                macroLayout.setVisibility(View.VISIBLE);
                macroView.setRotation(90);
                macroView.setTranslationY(dpToPx(6));
            }
            else{
                macroLayout.setVisibility(View.GONE);
                macroView.setRotation(270);
                macroView.setTranslationY(dpToPx(-6));
            }
        });

        microView.setOnClickListener(v -> {
            if(microLayout.getVisibility()==View.GONE){
                microLayout.setVisibility(View.VISIBLE);
                microView.setRotation(90);
                microView.setTranslationY(dpToPx(6));
            }
            else{
                microLayout.setVisibility(View.GONE);
                microView.setRotation(270);
                microView.setTranslationY(dpToPx(-6));
            }
        });

        vitaminView.setOnClickListener(v -> {
            if(vitaminLayout.getVisibility()==View.GONE){
                vitaminLayout.setVisibility(View.VISIBLE);
                vitaminView.setRotation(90);
                vitaminView.setTranslationY(dpToPx(6));
            }
            else{
                vitaminLayout.setVisibility(View.GONE);
                vitaminView.setRotation(270);
                vitaminView.setTranslationY(dpToPx(-6));
            }
        });




        // Inflate the layout for this fragment
        return view;
    }


    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }


}