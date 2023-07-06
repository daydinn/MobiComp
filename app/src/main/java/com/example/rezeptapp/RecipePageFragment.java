package com.example.rezeptapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
    boolean isRecipeOfflineSaved=false;

    RecyclerView macroRecyclerView;
    RecyclerView microRecyclerView;
    RecyclerView vitaminRecyclerView;
    RecyclerView ingredientRecyclerView;

    boolean isValid = false;

    /**
     * Requests recipe from API when in Online mode.
     * Requests recipe from Database when in Offline mode.
     * Sets recipe to invalid and load no data, if no id is given via bundle.
     * @Author Rene Wentzel
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Getting Recipe values from API or Database depending on online/offline mode                   Rene Wentzel
        //Sets isValid to false and stop initializing the recipe, if no id is found in bundle
        Bundle bundle = getArguments();
        if(bundle!=null){
            recipeID = bundle.getString("foundRecipe");
            isValid =true;
        }
        if (isValid){
            dbHandler = new DBHandler(getContext());
            if(MainActivity.isOnline){
                //Starts request to the API to get recipe data of recipeID.
                try {
                    SearchManager searchmanager = new SearchManager();
                    recipe = searchmanager.getRecipeByID(recipeID);
                    if(recipe.getId()==0)
                        isValid =false;
                } catch (InterruptedException e) {
                    isValid =false;
                }
            }
            else{
                recipe = dbHandler.getOfflineRecipeByID(recipeID);
            }
            //Ask database if recipe of given recipeID already exists
            isRecipeSaved = dbHandler.checkDataExists(recipeID);
            isRecipeOfflineSaved = dbHandler.checkOfflineDataExists(recipeID);

        }

    }


    /**
     * Sets up recipe, if recipe is valid.
     * Changes title of the toolbar and fills all views with respective recipe values.
     * Sets icons for diets/intolerances.
     * Sets up show/hide button for nutrient card views.
     * @Author Rene Wentzel
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_page, container, false);

        if (isValid) {
            //Set Optionbar Title
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(recipe.getTitle());
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
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
            setUpOptionMenu();

            view.post(new Runnable() {
                @Override
                public void run() {

                    recipeTitle.setText(recipe.getTitle());
                    if(MainActivity.isOnline)
                        Picasso.get().load(recipe.getImage()).into(recipeImage);
                    else
                        recipeImage.setImageResource(R.drawable.offline_image);
                    //setUpIngredients();
                    //setUpNutrients();
                    macroLayout.setVisibility(View.GONE);
                    microLayout.setVisibility(View.GONE);
                    vitaminLayout.setVisibility(View.GONE);
                    try {
                        instructions.setText(Html.fromHtml(recipe.getInstructions(), HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST));
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Unable to load Recipe.\nPlease reload Page.", Toast.LENGTH_SHORT).show();
                    }

                    //Setting up misc information
                    cookingTime.setText(recipe.getReadyInMinutes() + " minutes");
                    servings.setText(recipe.getServings() + " portions");
                    if (recipe.getCuisines().size() != 0) {
                        cuisineIcon.setAlpha(1f);
                        for (int i = 0; i < recipe.getCuisines().size(); i++) {
                            if (i != 0) {
                                cuisines.setText(cuisines.getText() + ", ");
                            }
                            cuisines.setText(cuisines.getText() + recipe.getCuisines().get(i));
                        }
                    }

                    if (recipe.isVegetarian())
                        vegetarian.setAlpha(1f);
                    if (recipe.isVegan())
                        vegan.setAlpha(1f);
                    if (recipe.isGlutenFree())
                        glutenFree.setAlpha(1f);
                    if (recipe.isDairyFree())
                        dairyFree.setAlpha(1f);
                    if (recipe.isLowFodmap())
                        lowFODMap.setAlpha(1f);
                    if (recipe.getHealthScore() != 0) {
                        healthScoreIcon.setAlpha(1f);
                        healthScore.setText(String.valueOf(recipe.getHealthScore()));
                    }
                    if (recipe.isSustainable())
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
                }
            });

            macroView.setOnClickListener(v -> {
                if (macroLayout.getVisibility() == View.GONE) {
                    macroLayout.setVisibility(View.VISIBLE);
                    macroView.setRotation(90);
                    macroView.setTranslationY(dpToPx(6));
                } else {
                    macroLayout.setVisibility(View.GONE);
                    macroView.setRotation(270);
                    macroView.setTranslationY(dpToPx(-6));
                }
            });

            microView.setOnClickListener(v -> {
                if (microLayout.getVisibility() == View.GONE) {
                    microLayout.setVisibility(View.VISIBLE);
                    microView.setRotation(90);
                    microView.setTranslationY(dpToPx(6));
                } else {
                    microLayout.setVisibility(View.GONE);
                    microView.setRotation(270);
                    microView.setTranslationY(dpToPx(-6));
                }
            });

            vitaminView.setOnClickListener(v -> {
                if (vitaminLayout.getVisibility() == View.GONE) {
                    vitaminLayout.setVisibility(View.VISIBLE);
                    vitaminView.setRotation(90);
                    vitaminView.setTranslationY(dpToPx(6));
                } else {
                    vitaminLayout.setVisibility(View.GONE);
                    vitaminView.setRotation(270);
                    vitaminView.setTranslationY(dpToPx(-6));
                }
            });


        }

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Sets up an Option Menu in the top ActionBar.
     * Includes saving/deleting a recipe to the local storage and setting/removing saved recipes to/from favorite.
     * @Author Rene Wentzel
     */
    private void setUpOptionMenu() {
        requireActivity().addMenuProvider(new MenuProvider() {
              @Override
              public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                  menu.clear();
                  menuInflater.inflate(R.menu.recipe_menu, menu);
                  isRecipeSaved = dbHandler.checkDataExists(recipeID);
                  isRecipeOfflineSaved = dbHandler.checkOfflineDataExists(recipeID);

                  //Sets optionmenu items depending on offline/online mode
                  if(MainActivity.isOnline){
                      recipe.setFavorite(dbHandler.isShortInfoFavorite(recipe.getId()));
                      menu.findItem(R.id.onlineModeItem).setVisible(false);
                      menu.findItem(R.id.offlineModeItem).setVisible(true);
                      if(isRecipeSaved)
                          menu.findItem(R.id.favoriteItem).setVisible(true);
                      else
                          menu.findItem(R.id.favoriteItem).setVisible(false);
                  }
                  else{
                      recipe.setFavorite(dbHandler.isOfflineDataFavorite(recipe.getId()));
                      menu.findItem(R.id.onlineModeItem).setVisible(true);
                      menu.findItem(R.id.offlineModeItem).setVisible(false);
                      if(isRecipeOfflineSaved)
                          menu.findItem(R.id.favoriteItem).setVisible(true);
                      else
                          menu.findItem(R.id.favoriteItem).setVisible(false);
                  }

                  //sets save/delete buttons depending on if recipe saved
                  if (isRecipeSaved) {
                      menu.findItem(R.id.recipeSaveItem).setVisible(false);
                      menu.findItem(R.id.recipeDeleteItem).setVisible(true);
                  } else {
                      menu.findItem(R.id.recipeSaveItem).setVisible(true);
                      menu.findItem(R.id.recipeDeleteItem).setVisible(false);
                  }

                  //sets offline save/delete and favorite buttons depending on if recipe saved
                  if (isRecipeOfflineSaved) {
                      menu.findItem(R.id.recipeSaveOfflineItem).setVisible(false);
                      menu.findItem(R.id.recipeDeleteOfflineItem).setVisible(true);
                  } else {
                      menu.findItem(R.id.recipeSaveOfflineItem).setVisible(true);
                      menu.findItem(R.id.recipeDeleteOfflineItem).setVisible(false);
                  }

                  if (recipe.isFavorite()) {
                      menu.findItem(R.id.favoriteItem).setIcon(R.drawable.favorite_on);
                  } else {
                      menu.findItem(R.id.favoriteItem).setIcon(R.drawable.favorite_off);
                  }
              }

              @Override
              public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                  //Save Button
                  if (menuItem.getItemId() == R.id.recipeSaveItem) {

                      if (recipe.getId() == 0) {
                          Snackbar.make(getView(), "Error!\nRecipe can't be saved", Snackbar.LENGTH_SHORT).show();
                          return true;
                      }

                      if (dbHandler.addNewShortInfo(recipe.getId(), recipe.getTitle(), recipe.getImage(), recipe.isFavorite())) {
                          Snackbar.make(getView(), "Recipe saved", Snackbar.LENGTH_SHORT).show();
                      } else {

                          Snackbar.make(getView(), "Error!\nRecipe can't be saved", Snackbar.LENGTH_SHORT).show();
                      }
                  }
                  //Favorite Icon
                  else if (menuItem.getItemId() == R.id.favoriteItem) {
                      recipe.setFavorite(!recipe.isFavorite());
                      if(MainActivity.isOnline){
                          if (dbHandler.updateShortInfoFavorite(recipe.getId(), recipe.isFavorite())) {
                              if (recipe.isFavorite()) {
                                  menuItem.setIcon(R.drawable.favorite_on);
                                  Snackbar.make(getView(), "Favorite Set", Snackbar.LENGTH_SHORT).show();
                              } else {
                                  menuItem.setIcon(R.drawable.favorite_off);
                                  Snackbar.make(getView(), "Favorite Removed", Snackbar.LENGTH_SHORT).show();
                              }
                          }
                      }
                      else{
                          if(dbHandler.updateOfflineRecipeFavorite(recipe.getId(), recipe.isFavorite())){
                              if (recipe.isFavorite()) {
                                  menuItem.setIcon(R.drawable.favorite_on);
                                  Snackbar.make(getView(), "Favorite Set", Snackbar.LENGTH_SHORT).show();
                              } else {
                                  menuItem.setIcon(R.drawable.favorite_off);
                                  Snackbar.make(getView(), "Favorite Removed", Snackbar.LENGTH_SHORT).show();
                              }
                          }
                      }
                  }

                  //Delete Button
                  else if (menuItem.getItemId() == R.id.recipeDeleteItem) {
                      if (dbHandler.deleteShortInfo(recipe.getId())) {
                          Snackbar.make(getView(), "Recipe Deleted", Snackbar.LENGTH_SHORT).show();
                      } else {
                          Snackbar.make(getView(), "Error!\nRecipe not deleted", Snackbar.LENGTH_SHORT).show();
                      }
                  }

                  //Share Button
                  else if (menuItem.getItemId() == R.id.recipeShareItem) {
                      if (recipe.getSourceUrl().isEmpty()) {
                          Snackbar.make(getView(), "Error!\nIt's not possible to share this recipe.", Snackbar.LENGTH_SHORT).show();
                      } else {
                          Intent sendIntent = new Intent();
                          sendIntent.setAction(Intent.ACTION_SEND);
                          sendIntent.putExtra(Intent.EXTRA_TEXT, recipe.getSourceUrl());
                          sendIntent.setType("text/plain");
                          Intent shareIntent = Intent.createChooser(sendIntent, null);
                          startActivity(shareIntent);
                      }
                  }

                  //Save Offline Button
                  else if (menuItem.getItemId() == R.id.recipeSaveOfflineItem) {
                      if (recipe.getId() == 0) {
                          Snackbar.make(getView(), "Error!\nRecipe can't be saved", Snackbar.LENGTH_SHORT).show();
                          return true;
                      }
                      if (dbHandler.addNewOfflineRecipe(recipe.getId(), recipe, recipe.isFavorite())) {
                          Snackbar.make(getView(), "Recipe saved", Snackbar.LENGTH_SHORT).show();

                      } else {
                          Snackbar.make(getView(), "Error!\nRecipe can't be saved", Snackbar.LENGTH_SHORT).show();
                      }
                  }

                  //Delete Offline Button
                  else if(menuItem.getItemId() == R.id.recipeDeleteOfflineItem){
                      if (dbHandler.deleteOfflineRecipe(recipe.getId())) {
                          Snackbar.make(getView(), "Recipe Deleted", Snackbar.LENGTH_SHORT).show();
                      } else {
                          Snackbar.make(getView(), "Error!\nRecipe not deleted", Snackbar.LENGTH_SHORT).show();
                      }
                  }

                  else if(menuItem.getItemId()==R.id.offlineModeItem){
                      MainActivity.isOnline=false;
                      Snackbar.make(getView(), "You are in Offline Mode now", Snackbar.LENGTH_SHORT).show();
                      getActivity().getSupportFragmentManager().popBackStack(null, getActivity().getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
                      getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                  }
                  else if(menuItem.getItemId()==R.id.onlineModeItem){
                      MainActivity.isOnline=true;
                      Snackbar.make(getView(), "You are in Online Mode now", Snackbar.LENGTH_SHORT).show();
                      getActivity().getSupportFragmentManager().popBackStack(null, getActivity().getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
                      getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                  }
                  requireActivity().invalidateOptionsMenu();
                  return true;
              }
          }, getViewLifecycleOwner()
        );
    }

    /**
     * Translates dp value to pixel value.
     * @Author Rene Wentzel
     * @param dp
     * @return returns dp value in pixel
     */
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }


}