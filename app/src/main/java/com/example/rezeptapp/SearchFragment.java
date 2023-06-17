package com.example.rezeptapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;

import java.util.ArrayList;
import java.util.HashMap;


public class SearchFragment extends Fragment {

    Button searchButton;
    SearchManager searchmanager = new SearchManager();
    private ArrayList<ShortInfo> shortInfoList = new ArrayList<>();
    HashMap<String, String> general = new HashMap<>();
    HashMap<String, Double> macronutrients = new HashMap<>();
    HashMap<String, Double> micronutrients = new HashMap<>();
    HashMap<String, Double> vitamins = new HashMap<>();

    EditText name;
    EditText ingredient;
    EditText excludedIngredient;

    //Diet
    Button diet;
    String[] dietItems;
    boolean[] checkedDiets;
    ArrayList<Integer> selectedDietsList = new ArrayList<>();
    boolean dietOr = false;

    //Cuisine
    Button cuisine;
    String[] cuisineItems;
    boolean[] checkedCuisines;
    ArrayList<Integer> selectedCuisineList = new ArrayList<>();
    boolean excludeCuisine = false;

    //Ingredient
    Button addIngredientButton;
    Button removeIngredientButton;
    LinearLayout ingredientLayout;
    Space spaceIngredient;
    ArrayList<EditText> ingredientEditList = new ArrayList<>();

    //Exclude Ingredient
    Button addExcludeIngredientButton;
    Button removeExcludeIngredientButton;
    LinearLayout excludeIngredientLayout;
    Space spaceExcludeIngredient;
    ArrayList<EditText> excludeIngredientEditList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Button searchButton = (Button) view.findViewById(R.id.buttonSearch);
        createSearchHashmaps();

        name = view.findViewById(R.id.editTextName);

        //Diet
        diet  = view.findViewById(R.id.DietButton);
        dietItems = getResources().getStringArray(R.array.diet_list);
        checkedDiets = new boolean[dietItems.length];

        //Cuisine
        cuisine = view.findViewById(R.id.CuisineButton);
        cuisineItems = getResources().getStringArray(R.array.cuisine_list);
        checkedCuisines = new boolean[cuisineItems.length];

        //Ingredients
        ingredient = view.findViewById(R.id.editTextIngredient);
        addIngredientButton = view.findViewById(R.id.addIngredientButton);
        removeIngredientButton = view. findViewById(R.id.removeIngredientButton);
        ingredientLayout = view.findViewById(R.id.ingredientsLayout);
        spaceIngredient = view.findViewById(R.id.spaceIngredient);

        //Excluded Ingredients
        excludedIngredient = view.findViewById(R.id.editTextExcludedIngredient);
        addExcludeIngredientButton = view.findViewById(R.id.addExcludeIngredientButton);
        removeExcludeIngredientButton = view.findViewById(R.id.removeExcludeIngredientButton);
        excludeIngredientLayout = view.findViewById(R.id.excludeIngredientsLayout);
        spaceExcludeIngredient = view.findViewById(R.id.spaceExcludeIngredient);

//Buttons: Add/Remove Ingredient Edit Text Fields -------------------------------------------------------------------
        removeIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ingredientEditList.size()>0){
                    ingredientLayout.removeView(ingredientEditList.get(ingredientEditList.size()-1));
                    ingredientEditList.remove(ingredientEditList.get(ingredientEditList.size()-1));
                }
                if(ingredientEditList.size()==0){
                    removeIngredientButton.setEnabled(false);
                    removeIngredientButton.setVisibility(View.GONE);
                }

            }
        });
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newEditText = new EditText(getActivity());
                newEditText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                newEditText.setHint("Ingredient");
                ingredientLayout.addView(newEditText, ingredientLayout.indexOfChild(spaceIngredient));
                ingredientEditList.add(newEditText);
                Log.d("sizep ingredient", String.valueOf(ingredientLayout.indexOfChild(spaceIngredient)));
                Log.d("sizep button", String.valueOf(ingredientLayout.indexOfChild(removeIngredientButton)));
                Log.d("sizep editlist", String.valueOf(ingredientLayout.indexOfChild(ingredientEditList.get(0))));
                if(ingredientEditList.size()==1){
                    removeIngredientButton.setEnabled(true);
                    removeIngredientButton.setVisibility(View.VISIBLE);
                }
            }
        });

//-----------------------------------------------------------------------------------------------------------------------


//Buttons: Add/Remove Excluded Ingredient Edit Text Fields -------------------------------------------------------------------
        removeExcludeIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(excludeIngredientEditList.size()>0){
                    excludeIngredientLayout.removeView(excludeIngredientEditList.get(excludeIngredientEditList.size()-1));
                    excludeIngredientEditList.remove(excludeIngredientEditList.get(excludeIngredientEditList.size()-1));
                }
                if(excludeIngredientEditList.size()==0){
                    removeExcludeIngredientButton.setEnabled(false);
                    removeExcludeIngredientButton.setVisibility(View.GONE);
                }

            }
        });
        addExcludeIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newEditText = new EditText(getActivity());
                newEditText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                newEditText.setHint("Excluded Ingredient");
                excludeIngredientLayout.addView(newEditText, excludeIngredientLayout.indexOfChild(spaceExcludeIngredient));
                excludeIngredientEditList.add(newEditText);
                if(excludeIngredientEditList.size()==1){
                    removeExcludeIngredientButton.setEnabled(true);
                    removeExcludeIngredientButton.setVisibility(View.VISIBLE);
                }
            }
        });

//-----------------------------------------------------------------------------------------------------------------------


//Button: Cuisine Selection -----------------------------------------------------------------------------------------------------
        cuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder cuisineAlert = new AlertDialog.Builder(getActivity());
                if(!excludeCuisine)
                    cuisineAlert.setTitle("Select Include Cuisines");
                else
                    cuisineAlert.setTitle("Select Exclude Cuisines");
                cuisineAlert.setMultiChoiceItems(cuisineItems, checkedCuisines, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int itemPosition, boolean isChecked) {
                        if(isChecked){
                            if(!selectedCuisineList.contains(itemPosition)){
                                selectedCuisineList.add(itemPosition);
                            }
                        }
                        else{
                            for (int i = 0; i < selectedCuisineList.size(); i++)
                                if (selectedCuisineList.get(i) == itemPosition) {
                                    selectedCuisineList.remove(i);
                                    break;}
                        }
                    }

                });
                cuisineAlert.setCancelable(true);
                cuisineAlert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder cuisineString= new StringBuilder();
                        for(int i=0;i<selectedCuisineList.size(); i++){
                            cuisineString.append(cuisineItems[selectedCuisineList.get(i)]);
                            if(i != selectedCuisineList.size()-1){
                                cuisineString.append(",");
                            }
                        }
                        if(excludeCuisine){
                            general.put("excludedCuisine", cuisineString.toString());
                            general.put("cuisine", "");
                        }
                        else{
                            general.put("cuisine", cuisineString.toString());
                            general.put("excludedCuisine", "");
                        }
                        Log.d("Selected Cuisines", cuisineString.toString());
                        Log.d("hash Cuisines", general.get("cuisine"));
                        Log.d("hash ExcludedCuisines", general.get("excludedCuisine"));
                    }
                });
                cuisineAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                cuisineAlert.setNeutralButton("In-/Exclude", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(excludeCuisine){
                            cuisineAlert.setTitle("Select Include Cuisines");
                            excludeCuisine=false;
                        }
                        else{
                            cuisineAlert.setTitle("Select Exclude Cuisines");
                            excludeCuisine=true;
                        }
                        cuisineAlert.show();

                    }
                });
                cuisineAlert.show();
            }
        });

//-----------------------------------------------------------------------------------------------------------------------

//Button: Diet Selection -----------------------------------------------------------------------------------------------------
        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dietAlert = new AlertDialog.Builder(getActivity());
                if(!dietOr)
                    dietAlert.setTitle("Include all Diets");
                else
                    dietAlert.setTitle("Include at least one Diet");
                dietAlert.setMultiChoiceItems(dietItems, checkedDiets, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int itemPosition, boolean isChecked) {
                        if(isChecked){
                            if(!selectedDietsList.contains(itemPosition)){
                                selectedDietsList.add(itemPosition);
                            }
                        }
                        else{
                            for (int i = 0; i < selectedDietsList.size(); i++)
                                if (selectedDietsList.get(i) == itemPosition) {
                                    selectedDietsList.remove(i);
                                    break;}
                        }
                    }
                });
                dietAlert.setCancelable(true);
                dietAlert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder dietString= new StringBuilder();
                        for(int i=0;i<selectedDietsList.size(); i++){
                            dietString.append(dietItems[selectedDietsList.get(i)]);
                            if(i != selectedDietsList.size()-1){
                                dietString.append(",");
                            }
                        }
                        if(dietOr){
                            //Befehl für OR Suche anhängen
                            general.put("diet", String.valueOf(dietString));
                        }
                        else{
                            //Befehl für AND Suche anhängen
                            general.put("diet", String.valueOf(dietString));
                        }
                        Log.d("search diet",general.get("diet") );
                    }
                });
                dietAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dietAlert.setNeutralButton("Mode", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dietOr){
                            dietAlert.setTitle("Include all Diets");
                            dietOr=false;
                        }
                        else{
                            dietAlert.setTitle("Include at least one Diet");
                            dietOr=true;
                        }
                        dietAlert.show();

                    }
                });
                dietAlert.show();
            }
        });

//-----------------------------------------------------------------------------------------------------------------------



// Button: Search -------------------------------------------------------------------------------------------------------------------
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread() {
                    public void run() {
                        try {
                            putValuesInHashmap();
                            shortInfoList = searchmanager.searchRecipes(general, macronutrients, micronutrients, vitamins);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                thread.start();

        /*Thread thread = new Thread(){
            public void run(){

                try {
                    recipe = searchmanager.getRecipeByID("637897");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        thread.start();*/

                SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, searchResultsFragment).commit();
            }
        });
        return view;
    }

//-----------------------------------------------------------------------------------------------------------------------


//HashMaps: Creating/Updating-----------------------------------------------------------------------------------------------------------------------
    private void putValuesInHashmap() {
        general.put("query", String.valueOf(name.getText()));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ingredient.getText());
        for(int i=0; i<ingredientEditList.size();i++){
            if(!String.valueOf(ingredientEditList.get(i).getText()).equals("")){
                stringBuilder.append(",").append(ingredientEditList.get(i).getText());
            }
        }
        general.put("includeIngredients", String.valueOf(stringBuilder));

        stringBuilder = new StringBuilder();
        stringBuilder.append(excludedIngredient.getText());
        for(int i=0; i<excludeIngredientEditList.size();i++){
            if(!String.valueOf(excludeIngredientEditList.get(i).getText()).equals("")){
                stringBuilder.append(",").append(excludeIngredientEditList.get(i).getText());
            }
        }
        general.put("excludeIngredients", String.valueOf(stringBuilder));
    }


    private void createSearchHashmaps() {
        general = new HashMap<>();
        general.put("query", "");
        general.put("cuisine", "");
        general.put("excludeCuisine", "");
        general.put("diet", "");
        general.put("intolerances", "");
        general.put("includeIngredients", "");
        general.put("excludeIngredients", "");
        general.put("type", "");
        general.put("maxReadyTime", "");

        macronutrients = new HashMap<>();
        macronutrients.put("minimumCarbs", -1.0);
        macronutrients.put("minimumProtein", -1.0);
        macronutrients.put("minimumFat", -1.0);
        macronutrients.put("minimumSaturatedFat", -1.0);
        macronutrients.put("minimumFiber", -1.0);
        macronutrients.put("minimumSugar", -1.0);
        macronutrients.put("maximumCarbs", -1.0);
        macronutrients.put("maximumProtein", -1.0);
        macronutrients.put("maximumFat", -1.0);
        macronutrients.put("maximumSaturatedFat", -1.0);
        macronutrients.put("maximumFiber", -1.0);
        macronutrients.put("maximumSugar", -1.0);

        micronutrients = new HashMap<>();
        micronutrients.put("minimumCalcium", -1.0);
        micronutrients.put("minimumCopper", -1.0);
        micronutrients.put("minimumIron", -1.0);
        micronutrients.put("minimumMagnesium", -1.0);
        micronutrients.put("minimumManganese", -1.0);
        micronutrients.put("minimumPhosphorus", -1.0);
        micronutrients.put("minimumPotassium", -1.0);
        micronutrients.put("minimumSelenium", -1.0);
        micronutrients.put("minimumSodium", -1.0);
        micronutrients.put("minimumZinc", -1.0);
        micronutrients.put("minimumCholine", -1.0);
        micronutrients.put("minimumFolate", -1.0);
        micronutrients.put("minimumFolicAcid", -1.0);
        micronutrients.put("minimumIodine", -1.0);
        micronutrients.put("maximumCalcium", -1.0);
        micronutrients.put("maximumCopper", -1.0);
        micronutrients.put("maximumIron", -1.0);
        micronutrients.put("maximumMagnesium", -1.0);
        micronutrients.put("maximumManganese", -1.0);
        micronutrients.put("maximumPhosphorus", -1.0);
        micronutrients.put("maximumPotassium", -1.0);
        micronutrients.put("maximumSelenium", -1.0);
        micronutrients.put("maximumSodium", -1.0);
        micronutrients.put("maximumZinc", -1.0);
        micronutrients.put("maximumCholine", -1.0);
        micronutrients.put("maximumFolate", -1.0);
        micronutrients.put("maximumFolicAcid", -1.0);
        micronutrients.put("maximumIodine", -1.0);


        vitamins = new HashMap<>();
        vitamins.put("minimumA", -1.0);
        vitamins.put("minimumC", -1.0);
        vitamins.put("minimumD", -1.0);
        vitamins.put("minimumE", -1.0);
        vitamins.put("minimumK", -1.0);
        vitamins.put("minimumB1", -1.0);
        vitamins.put("minimumB2", -1.0);
        vitamins.put("minimumB3", -1.0);
        vitamins.put("minimumB5", -1.0);
        vitamins.put("minimumB6", -1.0);
        vitamins.put("minimumB12", -1.0);
        vitamins.put("maximumA", -1.0);
        vitamins.put("maximumC", -1.0);
        vitamins.put("maximumD", -1.0);
        vitamins.put("maximumE", -1.0);
        vitamins.put("maximumK", -1.0);
        vitamins.put("maximumB1", -1.0);
        vitamins.put("maximumB2", -1.0);
        vitamins.put("maximumB3", -1.0);
        vitamins.put("maximumB5", -1.0);
        vitamins.put("maximumB6", -1.0);
        vitamins.put("maximumB12", -1.0);


    }

    //------------------------------------------------------------------------------------------------------------------
}


/*


     SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
                     getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,searchResultsFragment).commit();
 */

