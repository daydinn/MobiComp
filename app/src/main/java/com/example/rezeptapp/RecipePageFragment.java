package com.example.rezeptapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipePageFragment extends Fragment {
    String recipeID ="";
    Recipe recipe = new Recipe();
    DBHandler dbHandler;
    TextView recipeTitle;
    ImageView recipeImage;
    LinearLayout ingredientLayout;
    TextView instructions;


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
            recipe = searchmanager.getRecipeByID(recipeID);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



        dbHandler = new DBHandler(getContext());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_page, container, false);

        recipeTitle = view.findViewById(R.id.RecipeTitleTextView);
        recipeTitle.setText(recipe.getTitle());
        recipeImage = view.findViewById(R.id.bigRecipeImage);
        Picasso.get().load(recipe.getImage()).into(recipeImage);
        ingredientLayout = view.findViewById(R.id.RecipeIngredientsLayout);
        setUpIngredients();
        instructions = view.findViewById(R.id.RecipeInstruction);
        instructions.setText(Html.fromHtml(recipe.getInstructions(), HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST));

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(recipe.getTitle());

        /**
         * Sets up an Option Menu in the top ActionBar.
         * Includes saving a recipe
         * To do: Erweitern sobald fertig
         * @Author Rene Wentzel
         */
        requireActivity().addMenuProvider(new MenuProvider() {

              @Override
              public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                  menuInflater.inflate(R.menu.recipe_menu, menu);

                  //menu.findItem(R.id.favoriteItem).setVisible(false);
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

                  }
              //Favorite Icon
                  else if(menuItem.getItemId() == R.id.favoriteItem){
                      /*boolean newFavoriteState;
                      if(recipe.isFavorite()){
                          newFavoriteState=false;
                      }
                      else{
                          newFavoriteState=true;
                      }*/
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

                      }

                      //menuItem.setVisible(false);
                  }
                  /*else if(menuItem.getItemId() == R.id.recipeUpdateItem){
                      if(dbHandler.updateShortInfo(5,"newTitle", "newImage.url", true)){
                          Snackbar.make(getView(), "Recipe Updated", Snackbar.LENGTH_SHORT).show();
                      }
                      else{
                          Snackbar.make(getView(), "Error!\nRecipe not updated", Snackbar.LENGTH_SHORT).show();
                      }
                  }*/
                  else if(menuItem.getItemId() == R.id.recipeDeleteItem){
                      if(dbHandler.deleteShortInfo(recipe.getId())){
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
        return view;
    }

    /**
     * Creates a dedicated Layout for each ingredient and their amount and unit and add them to the Page.
     * The layout creation can be found in createIngredientLine() methode.
     * @Author Rene Wentzel
     */
    private void setUpIngredients(){
        ArrayList<Recipe.ExtendedIngredient> ingredientList = recipe.getExtendedIngredients();
        if(ingredientList!=null){
            for(int i=0; i<ingredientList.size();i++){
                ingredientLayout.addView(
                        createIngredientLine(ingredientList.get(i).getName(),
                            String.valueOf(ingredientList.get(i).getAmount()),
                            ingredientList.get(i).getUnit())
                );
            }
        }

    }

    /**
     * Creates a linear layout that includes the given ingredient, amount and unit.
     * returns the created linear layout.
 * @Autor Rene Wentzel
     * @param ingredient The ingredient name
     * @param measure The measurement of the ingredient
     * @param unit The unit of the measurement
     * @return Returns a LinearLayout object
     */
    private LinearLayout createIngredientLine(String ingredient, String measure, String unit){
        int sizeInDP =0;
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        sizeInDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        linearLayout.setPadding(0,sizeInDP,0,sizeInDP);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

    // First TextView (RecipeIngredient)
        TextView recipeIngredientTextView = new TextView(getContext());
        recipeIngredientTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));
        recipeIngredientTextView.setGravity(Gravity.START);
        sizeInDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics());
        int sizeInDP2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        recipeIngredientTextView.setPadding(sizeInDP, 0, 0, sizeInDP2);
        recipeIngredientTextView.setText(ingredient);

    // Second TextView (RecipeMeasure)
        TextView recipeMeasureTextView = new TextView(getContext());
        recipeMeasureTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));
        recipeMeasureTextView.setGravity(Gravity.END);
        sizeInDP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics());
        recipeMeasureTextView.setPadding(0, 0, sizeInDP, sizeInDP2);
        recipeMeasureTextView.setText(measure + " " + unit);

        // Füge die TextViews dem LinearLayout hinzu
        linearLayout.addView(recipeIngredientTextView);
        linearLayout.addView(recipeMeasureTextView);

        //ingredientLayout.addView(linearLayout,1);
        return linearLayout;
    }


}