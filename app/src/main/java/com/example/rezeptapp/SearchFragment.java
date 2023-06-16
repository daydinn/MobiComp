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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
    Button cuisine;
    Button diet;
    String[] cuisineItems;
    boolean[] checkedCuisines;
    ArrayList<Integer> selectedCuisineList = new ArrayList<>();
    boolean excludeCuisine =false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Button searchButton = (Button) view.findViewById(R.id.buttonSearch);
        createSearchHashmaps();

        name = view.findViewById(R.id.editTextName);
        ingredient = view.findViewById(R.id.editTextIngredient);
        excludedIngredient = view.findViewById(R.id.editTextExcludedIngredient);
        cuisine = view.findViewById(R.id.CuisineButton);
        diet = view.findViewById(R.id.DietButton);
        cuisineItems = getResources().getStringArray(R.array.cuisine_list);
        checkedCuisines = new boolean[cuisineItems.length];
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
                            else{
                                selectedCuisineList.remove(itemPosition);
                            }
                        }
                    }

                });
                cuisineAlert.setCancelable(true);
                cuisineAlert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cuisineString="";
                        for(int i=0;i<selectedCuisineList.size(); i++){
                            cuisineString= cuisineString + cuisineItems[selectedCuisineList.get(i)];
                            if(i != selectedCuisineList.size()-1){
                                cuisineString = cuisineString + ",";
                            }
                        }
                        if(excludeCuisine){
                            general.put("excludedCuisine", cuisineString);
                            general.put("cuisine", "");
                        }
                        else{
                            general.put("cuisine", cuisineString);
                            general.put("excludedCuisine", "");
                        }
                        Log.d("Selected Cuisines", cuisineString);
                        Log.d("hash Cuisines", general.get("cuisine"));
                        Log.d("hash ExcludedCuisines", general.get("excludedCuisine"));
                    }
                });
                cuisineAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                cuisineAlert.setNeutralButton("Include/Exclude", new DialogInterface.OnClickListener() {
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

    private void putValuesInHashmap() {
        general = new HashMap<>();
        general.put("query", String.valueOf(name.getText()));
        general.put("includeIngredients", String.valueOf(ingredient.getText()));
        general.put("excludeIngredients", String.valueOf(excludedIngredient.getText()));
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
}


/*


     SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
                     getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,searchResultsFragment).commit();
 */

