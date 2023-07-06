package com.example.rezeptapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Space;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class SearchFragment extends Fragment {

    Button searchButton;
    SearchManager searchmanager = new SearchManager();
    private ArrayList<ShortInfo> shortInfoList = new ArrayList<>();
    HashMap<String, String> general = new HashMap<>();
    HashMap<String, String> macronutrients = new HashMap<>();
    HashMap<String, String> micronutrients = new HashMap<>();
    HashMap<String, String> vitamins = new HashMap<>();

    EditText name;
    EditText ingredient;
    EditText excludedIngredient;

    //Cuisine
    Button cuisine;
    String[] cuisineItems;
    boolean[] checkedCuisines;
    ArrayList<Integer> selectedCuisineList = new ArrayList<>();
    boolean excludeCuisine = false;

    //Diet
    Button diet;
    String[] dietItems;
    boolean[] checkedDiets;
    ArrayList<Integer> selectedDietsList = new ArrayList<>();
    boolean dietOr = false;

    //Intolerances
    Button intolerances;
    String[] intolerancesItems;
    boolean[] checkedIntolerances;
    ArrayList<Integer> selectedIntolerancesList = new ArrayList<>();

    //Intolerances
    Button type;
    String[] typeItems;
    boolean[] checkedTypes;
    ArrayList<Integer> selectedTypeList = new ArrayList<>();


    //Ingredient
    ImageView addIngredientButton;
    ImageView removeIngredientButton;
    LinearLayout ingredientLayout;
    Space spaceIngredient;
    ArrayList<EditText> ingredientEditList = new ArrayList<>();

    //Exclude Ingredient
    ImageView addExcludeIngredientButton;
    ImageView removeExcludeIngredientButton;
    LinearLayout excludeIngredientLayout;
    Space spaceExcludeIngredient;
    ArrayList<EditText> excludeIngredientEditList = new ArrayList<>();

    //Macro Nutrients
    LinearLayout macroLayout;
    ArrayList<LinearLayout> macroNutrientList = new ArrayList<>();
    ImageView macroButton;

    //Micro Nutrients
    LinearLayout microLayout;
    ArrayList<LinearLayout> microNutrientList = new ArrayList<>();
    ImageView microButton;
    CardView microCard;

    //Vitamins
    LinearLayout vitaminLayout;
    ArrayList<LinearLayout> vitaminList = new ArrayList<>();
    ImageView vitaminButton;

    //Max. Cooking Time
    SeekBar timePicker;
    TextView minuteText;

    /**
     * Sets up all fields of the page.
     * Sets button to add/Remove ingredient and excluded-ingredient editTextViews.
     * Sets up dialogues for cuisine, diet, intolerace and dysh type selection.
     * Sets up listener for search button.
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        //Set Optionbar Title
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Search Recipe");
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        assert appCompatActivity != null;
        Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);

        searchButton = view.findViewById(R.id.buttonSearch);
        createSearchHashmaps();

        name = view.findViewById(R.id.editTextName);

        //Cuisine
        cuisine = view.findViewById(R.id.CuisineButton);
        cuisineItems = getResources().getStringArray(R.array.cuisine_list);
        checkedCuisines = new boolean[cuisineItems.length];

        //Diet
        diet = view.findViewById(R.id.DietButton);
        dietItems = getResources().getStringArray(R.array.diet_list);
        checkedDiets = new boolean[dietItems.length];

        //Intolerances
        intolerances = view.findViewById(R.id.IntolerancesButton);
        intolerancesItems = getResources().getStringArray(R.array.intolerance_list);
        checkedIntolerances = new boolean[intolerancesItems.length];

        //Type
        type = view.findViewById(R.id.TypeButton);
        typeItems = getResources().getStringArray(R.array.type_list);
        checkedTypes = new boolean[typeItems.length];

        //Ingredients
        ingredient = view.findViewById(R.id.editTextIngredient);
        addIngredientButton = view.findViewById(R.id.addIngredientButton);
        removeIngredientButton = view.findViewById(R.id.removeIngredientButton);
        ingredientLayout = view.findViewById(R.id.ingredientsLayout);
        spaceIngredient = view.findViewById(R.id.spaceIngredient);

        //Excluded Ingredients
        excludedIngredient = view.findViewById(R.id.editTextExcludedIngredient);
        addExcludeIngredientButton = view.findViewById(R.id.addExcludeIngredientButton);
        removeExcludeIngredientButton = view.findViewById(R.id.removeExcludeIngredientButton);
        excludeIngredientLayout = view.findViewById(R.id.excludeIngredientsLayout);
        spaceExcludeIngredient = view.findViewById(R.id.spaceExcludeIngredient);

        //Nutrients+Vitamins
        macroLayout = view.findViewById(R.id.MacroLayout);
        macroButton = view.findViewById(R.id.macroButton);
        microLayout = view.findViewById(R.id.MicroLayout);
        microButton = view.findViewById(R.id.microButton);
        microCard = view.findViewById(R.id.RecipeIngredientCard);
        vitaminLayout = view.findViewById(R.id.VitaminLayout);
        vitaminButton = view.findViewById(R.id.vitaminButton);

        //Timepicker
        timePicker = view.findViewById(R.id.minutePicker);
        minuteText = view.findViewById(R.id.minutesTextView);

        //Online-Offline Mode
        CardView nameCard = view.findViewById(R.id.searchNameCard);
        CardView ingredientCard = view.findViewById(R.id.searchIngredientCard);
        CardView excludedIngredientCard = view.findViewById(R.id.searchExcludedIngredientCard);
        CardView selectableCard = view.findViewById(R.id.searchselectableCard);
        TextView offlineText = view.findViewById(R.id.noSearchAvailable);

        if(!MainActivity.isOnline){
            nameCard.setVisibility(View.GONE);
            ingredientCard.setVisibility(View.GONE);
            excludedIngredientCard.setVisibility(View.GONE);
            selectableCard.setVisibility(View.GONE);
            macroLayout.setVisibility(View.GONE);
            microLayout.setVisibility(View.GONE);
            vitaminLayout.setVisibility(View.GONE);
            searchButton.setVisibility(View.GONE);
            offlineText.setVisibility(View.VISIBLE);
        }

        setUpOptionMenu();

        view.post(new Runnable() {
            @Override
            public void run() {

                timePicker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        minuteText.setText("Minutes: "+String.valueOf(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

//Buttons: Add/Remove Ingredient Edit Text Fields-------------------------------------------------------------------
                /**
                 * Removes the latest EditText of ingredients
                 * @Author Rene Wentzel
                 */
                removeIngredientButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ingredientEditList.size() > 0) {
                            ingredientLayout.removeView(ingredientEditList.get(ingredientEditList.size() - 1));
                            ingredientEditList.remove(ingredientEditList.get(ingredientEditList.size() - 1));
                        }
                        if (ingredientEditList.size() == 0) {
                            removeIngredientButton.setEnabled(false);
                            removeIngredientButton.setVisibility(View.GONE);
                        }

                    }
                });

                /**
                 * Adds a new EditText for ingredients
                 * @Author Rene Wentzel
                 */
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
                        if (ingredientEditList.size() == 1) {
                            removeIngredientButton.setEnabled(true);
                            removeIngredientButton.setVisibility(View.VISIBLE);
                        }
                    }
                });

//-----------------------------------------------------------------------------------------------------------------------


//Buttons: Add/Remove Excluded Ingredient Edit Text Fields -------------------------------------------------------------------
                /**
                 * Removes the latest EditText of excluded ingredients
                 * @Author Rene Wentzel
                 */
                removeExcludeIngredientButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (excludeIngredientEditList.size() > 0) {
                            excludeIngredientLayout.removeView(excludeIngredientEditList.get(excludeIngredientEditList.size() - 1));
                            excludeIngredientEditList.remove(excludeIngredientEditList.get(excludeIngredientEditList.size() - 1));
                        }
                        if (excludeIngredientEditList.size() == 0) {
                            removeExcludeIngredientButton.setEnabled(false);
                            removeExcludeIngredientButton.setVisibility(View.GONE);
                        }

                    }
                });

                /**
                 * Adds a new EditText for excluded ingredients
                 * @Author Rene Wentzel
                 */
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
                        if (excludeIngredientEditList.size() == 1) {
                            removeExcludeIngredientButton.setEnabled(true);
                            removeExcludeIngredientButton.setVisibility(View.VISIBLE);
                        }
                    }
                });

//-----------------------------------------------------------------------------------------------------------------------


//Button: Cuisine Selection-----------------------------------------------------------------------------------------------------
                /**
                 * Opens a dialog with a check list of cuisines.
                 * Writes selected cuisines in dedicated Hashmaps when confirming.
                 * @Author Rene Wentzel
                 */
                cuisine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder cuisineAlert = new AlertDialog.Builder(getActivity());
                        if (!excludeCuisine)
                            cuisineAlert.setTitle("Select Include Cuisines");
                        else
                            cuisineAlert.setTitle("Select Exclude Cuisines");
                        cuisineAlert.setMultiChoiceItems(cuisineItems, checkedCuisines, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int itemPosition, boolean isChecked) {
                                if (isChecked) {
                                    if (!selectedCuisineList.contains(itemPosition)) {
                                        selectedCuisineList.add(itemPosition);
                                    }
                                } else {
                                    for (int i = 0; i < selectedCuisineList.size(); i++)
                                        if (selectedCuisineList.get(i) == itemPosition) {
                                            selectedCuisineList.remove(i);
                                            break;
                                        }
                                }
                            }

                        });
                        cuisineAlert.setCancelable(true);
                        cuisineAlert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StringBuilder cuisineString = new StringBuilder();
                                for (int i = 0; i < selectedCuisineList.size(); i++) {
                                    cuisineString.append(cuisineItems[selectedCuisineList.get(i)]);
                                    if (i != selectedCuisineList.size() - 1) {
                                        cuisineString.append(",");
                                    }
                                }
                                if (excludeCuisine) {
                                    general.put("excludedCuisine", cuisineString.toString());
                                    general.put("cuisine", "");
                                } else {
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
                                if (excludeCuisine) {
                                    cuisineAlert.setTitle("Select Include Cuisines");
                                    excludeCuisine = false;
                                } else {
                                    cuisineAlert.setTitle("Select Exclude Cuisines");
                                    excludeCuisine = true;
                                }
                                cuisineAlert.show();

                            }
                        });
                        cuisineAlert.show();
                    }
                });

//-----------------------------------------------------------------------------------------------------------------------

//Button: Diet Selection-----------------------------------------------------------------------------------------------------
                /**
                 * Opens a dialog with a check list of diets.
                 * Writes selected diet types in dedicated Hashmaps when confirming.
                 * @Author Rene Wentzel
                 */
                diet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder dietAlert = new AlertDialog.Builder(getActivity());
                        if (!dietOr)
                            dietAlert.setTitle("Include all Diets");
                        else
                            dietAlert.setTitle("Include at least one Diet");
                        dietAlert.setMultiChoiceItems(dietItems, checkedDiets, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int itemPosition, boolean isChecked) {
                                if (isChecked) {
                                    if (!selectedDietsList.contains(itemPosition)) {
                                        selectedDietsList.add(itemPosition);
                                    }
                                } else {
                                    for (int i = 0; i < selectedDietsList.size(); i++)
                                        if (selectedDietsList.get(i) == itemPosition) {
                                            selectedDietsList.remove(i);
                                            break;
                                        }
                                }
                            }
                        });
                        dietAlert.setCancelable(true);
                        dietAlert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StringBuilder dietString = new StringBuilder();
                                for (int i = 0; i < selectedDietsList.size(); i++) {
                                    dietString.append(dietItems[selectedDietsList.get(i)]);
                                    if (i != selectedDietsList.size() - 1) {
                                        dietString.append(",");
                                    }
                                }
                                if (dietOr) {
                                    //Befehl für OR Suche anhängen
                                    general.put("diet", String.valueOf(dietString));
                                } else {
                                    //Befehl für AND Suche anhängen
                                    general.put("diet", String.valueOf(dietString));
                                }
                                Log.d("search diet", general.get("diet"));
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
                                if (dietOr) {
                                    dietAlert.setTitle("Include all Diets");
                                    dietOr = false;
                                } else {
                                    dietAlert.setTitle("Include at least one Diet");
                                    dietOr = true;
                                }
                                dietAlert.show();
                            }
                        });
                        dietAlert.show();
                    }
                });

//-----------------------------------------------------------------------------------------------------------------------

//Button: Intolerance Selection-----------------------------------------------------------------------------------------------------
                /**
                 * Opens a dialog with a check list of intolerances.
                 * Writes selected intolerances in dedicated Hashmaps when confirming.
                 * @Author Rene Wentzel
                 */
                intolerances.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder intolerancesAlert = new AlertDialog.Builder(getActivity());
                        intolerancesAlert.setTitle("Select Intolerances");
                        intolerancesAlert.setMultiChoiceItems(intolerancesItems, checkedIntolerances, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int itemPosition, boolean isChecked) {
                                if (isChecked) {
                                    if (!selectedIntolerancesList.contains(itemPosition)) {
                                        selectedIntolerancesList.add(itemPosition);
                                    }
                                } else {
                                    for (int i = 0; i < selectedIntolerancesList.size(); i++)
                                        if (selectedIntolerancesList.get(i) == itemPosition) {
                                            selectedIntolerancesList.remove(i);
                                            break;
                                        }
                                }
                            }
                        });
                        intolerancesAlert.setCancelable(true);
                        intolerancesAlert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StringBuilder intolerancesString = new StringBuilder();
                                for (int i = 0; i < selectedIntolerancesList.size(); i++) {
                                    intolerancesString.append(intolerancesItems[selectedIntolerancesList.get(i)]);
                                    if (i != selectedIntolerancesList.size() - 1) {
                                        intolerancesString.append(",");
                                    }
                                }
                                general.put("intolerances", String.valueOf(intolerancesString));
                            }
                        });
                        intolerancesAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        intolerancesAlert.show();
                    }
                });

//-----------------------------------------------------------------------------------------------------------------------

//Button: Type Selection-----------------------------------------------------------------------------------------------------
                /**
                 * Opens a dialog with a check list of meal types.
                 * Writes selected meal types in dedicated Hashmaps when confirming.
                 * @Author Rene Wentzel
                 */
                type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder typeAlert = new AlertDialog.Builder(getActivity());
                        typeAlert.setTitle("Select Intolerances");
                        typeAlert.setMultiChoiceItems(typeItems, checkedTypes, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int itemPosition, boolean isChecked) {
                                if (isChecked) {
                                    if (!selectedTypeList.contains(itemPosition)) {
                                        selectedTypeList.add(itemPosition);
                                    }
                                } else {
                                    for (int i = 0; i < selectedTypeList.size(); i++)
                                        if (selectedTypeList.get(i) == itemPosition) {
                                            selectedTypeList.remove(i);
                                            break;
                                        }
                                }
                            }
                        });
                        typeAlert.setCancelable(true);
                        typeAlert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StringBuilder typeString = new StringBuilder();
                                for (int i = 0; i < selectedTypeList.size(); i++) {
                                    typeString.append(typeItems[selectedTypeList.get(i)]);
                                    if (i != selectedTypeList.size() - 1) {
                                        typeString.append(",");
                                    }
                                }
                                general.put("type", String.valueOf(typeString));
                            }
                        });
                        typeAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        typeAlert.show();
                    }
                });

//-----------------------------------------------------------------------------------------------------------------------


// Button: Search-------------------------------------------------------------------------------------------------------------------
                /**
                 * Starts the search.
                 * Gives all search values to the SearchManager and hands the search results over to the SearchResultsFragment
                 * Goes to SearchResultFragment
                 * @Author Rene Wentzel
                 */
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Thread thread = new Thread() {
                            public void run() {
                                try {
                                    putValuesInHashmap();
                                    shortInfoList = searchmanager.searchRecipes(general, macronutrients, micronutrients, vitamins);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("searchResults", shortInfoList);
                                    SearchResultsFragment searchResultsFragment = new SearchResultsFragment();
                                    searchResultsFragment.setArguments(bundle);
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, searchResultsFragment).addToBackStack(null).commit();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        };
                        thread.start();
                    }
                });
//-----------------------------------------------------------------------------------------------------------------------

//Create Nutrient Fields                Rene Wentzel -----------------------------------------------------------------------------------------------
                createMacroNutrientList();
                createMicroNutrientList();
                createVitaminList();

//-----------------------------------------------------------------------------------------------------------------------

//Nutrient Buttons-----------------------------------------------------------------------------------------------
                /**
                 * Shows/hides the macro nutrients in search page.
                 * @Author Rene Wentzel
                 */
                macroButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (macroNutrientList.get(0).getVisibility() == View.GONE) {
                            for (int i = 0; i < macroNutrientList.size(); i++) {
                                macroNutrientList.get(i).setVisibility(view.VISIBLE);
                            }
                            macroButton.setRotation(90);
                            macroButton.setTranslationY(dpToPx(6));
                        } else {
                            for (int i = 0; i < macroNutrientList.size(); i++) {
                                macroNutrientList.get(i).setVisibility(view.GONE);
                            }
                            macroButton.setRotation(270);
                            macroButton.setTranslationY(dpToPx(-6));
                        }
                    }
                });

                /**
                 * Shows/hides the micro nutrients in search page.
                 * @Author Rene Wentzel
                 */
                microButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (microNutrientList.get(0).getVisibility() == View.GONE) {
                            //TransitionManager.beginDelayedTransition(view.findViewById(R.id.MicroCard), new AutoTransition());
                            for (int i = 0; i < microNutrientList.size(); i++) {
                                microNutrientList.get(i).setVisibility(view.VISIBLE);
                            }
                            microButton.setRotation(90);
                            microButton.setTranslationY(dpToPx(6));
                        } else {
                            for (int i = 0; i < microNutrientList.size(); i++) {
                                microNutrientList.get(i).setVisibility(view.GONE);
                            }
                            microButton.setRotation(270);
                            microButton.setTranslationY(dpToPx(-6));
                        }
                    }
                });

                /**
                 * Shows/hides the vitamins in search page.
                 * @Author Rene Wentzel
                 */
                vitaminButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (vitaminList.get(0).getVisibility() == View.GONE) {
                            for (int i = 0; i < vitaminList.size(); i++) {
                                vitaminList.get(i).setVisibility(view.VISIBLE);
                            }
                            vitaminButton.setRotation(90);
                            vitaminButton.setTranslationY(dpToPx(6));
                        } else {
                            for (int i = 0; i < vitaminList.size(); i++) {
                                vitaminList.get(i).setVisibility(view.GONE);
                            }
                            vitaminButton.setRotation(270);
                            vitaminButton.setTranslationY(dpToPx(-6));
                        }
                    }
                });

//-----------------------------------------------------------------------------------------------------------------------

            }
        });


//End of Create View

        return view;
    }

    /**
     * Sets up an Option Menu in the top ActionBar.
     * Includes options to switch between offline and online mode.
     * @Author Rene Wentzel
     */
    private void setUpOptionMenu() {

        requireActivity().addMenuProvider(new MenuProvider() {
              @Override
              public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                  menu.clear();
                  menuInflater.inflate(R.menu.recipe_menu, menu);
                  if(MainActivity.isOnline){
                      menu.findItem(R.id.onlineModeItem).setVisible(false);
                      menu.findItem(R.id.offlineModeItem).setVisible(true);
                  }
                  else{
                      menu.findItem(R.id.onlineModeItem).setVisible(true);
                      menu.findItem(R.id.offlineModeItem).setVisible(false);
                  }
                  menu.findItem(R.id.recipeSaveItem).setVisible(false);
                  menu.findItem(R.id.recipeDeleteItem).setVisible(false);
                  menu.findItem(R.id.recipeSaveOfflineItem).setVisible(false);
                  menu.findItem(R.id.recipeDeleteOfflineItem).setVisible(false);
                  menu.findItem(R.id.recipeShareItem).setVisible(false);
                  menu.findItem(R.id.favoriteItem).setVisible(false);
              }

              @Override
              public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                  if(menuItem.getItemId()==R.id.offlineModeItem){
                      MainActivity.isOnline=false;
                      Snackbar.make(getView(), "You are in Offline Mode now", Snackbar.LENGTH_SHORT).show();
                  }
                  else if(menuItem.getItemId()==R.id.onlineModeItem){
                      MainActivity.isOnline=true;
                      Snackbar.make(getView(), "You are in Online Mode now", Snackbar.LENGTH_SHORT).show();
                  }
                  requireActivity().invalidateOptionsMenu();
                  getActivity().getSupportFragmentManager().popBackStack(null, getActivity().getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
                  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).addToBackStack(null).commit();

                  return true;
              }
          }, getViewLifecycleOwner()
        );
    }

    //Create Nutrient Fields-----------------------------------------------------------------------------------------------

    /**
     * Creates a certain layout for each micro nutrient that can be searched for.
     * Puts each created layout into an ArrayList of Layouts (microNutrientList).
     * The layout creation can be found in creatNutrientViews() methode
     * @Author Rene Wentzel
     */
    private void createMicroNutrientList() {
        String[] names = getResources().getStringArray(R.array.micronutrient_list);
        String[] measure = getResources().getStringArray(R.array.micronutrient_measure_list);
        for (int i=0;i<names.length;i++) {
            microNutrientList.add(createNutrientViews(names[i],measure[i], microLayout));
        }
    }

    /**
     * Creates a certain layout for each macro nutrient that can be searched for.
     * Puts each created layout into an ArrayList of Layouts (macroNutrientList).
     * The layout creation can be found in creatNutrientViews() methode
     * @Author Rene Wentzel
     */
    private void createMacroNutrientList() {
        String[] names = getResources().getStringArray(R.array.macronutrient_list);
        String[] measure = getResources().getStringArray(R.array.macronutrient_measure_list);
        for (int i=0;i<names.length;i++) {
            macroNutrientList.add(createNutrientViews(names[i],measure[i], macroLayout));
        }
    }

    /**
     * Creates a certain layout for each vitamin that can be searched for.
     * Puts each created layout into an ArrayList of Layouts (vitaminList).
     * The layout creation can be found in creatNutrientViews() methode
     * @Author Rene Wentzel
     */
    private void createVitaminList() {
        String[] names = getResources().getStringArray(R.array.vitamin_list);
        String[] measure = getResources().getStringArray(R.array.vitamin_measure_list);
        for (int i=0;i<names.length;i++) {
            vitaminList.add(createNutrientViews(names[i],measure[i], vitaminLayout));
        }
    }

    //ToDo: Wenn Zeit durch RecyclerView ersetzen
    /**
     * Creates a layout with a textview for a given nutrient and measurement units
     * and with two editText fields for a min. and max. value of the given name.
     * Returns the created layout.
     * @Author Rene Wentzel
     * @param name The name of nutrient
     * @param layout The layout that adds the created layout
     * @return Returns a LinearLayout object.
     */
    private LinearLayout createNutrientViews(String name,String measure, LinearLayout layout) {
        LinearLayout linear = new LinearLayout(getContext());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(50, layoutParams.topMargin, 50, layoutParams.bottomMargin);
        linear.setLayoutParams(layoutParams);
        linear.setOrientation(LinearLayout.HORIZONTAL);

        // Textviews
        TextView textView1 = new TextView(getContext());
        textView1.setText(name);
        textView1.setTextSize(16);
        float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 90, getResources().getDisplayMetrics());
        textView1.setLayoutParams(new LinearLayout.LayoutParams(
                (int) width,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));

        TextView textView2 = new TextView(getContext());
        textView2.setText("min.");
        textView2.setTextSize(16);
        textView2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));

        //EditText Min
        EditText editText = new EditText(getContext());
        editText.setHint("0");
        editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText.setKeyListener(DigitsKeyListener.getInstance(false, true));
        width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                (int) width,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));

        TextView textView3 = new TextView(getContext());
        textView3.setText(measure);
        textView3.setTextSize(16);
        textView3.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));

        //Space
        Space space = new Space(getContext());
        width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        space.setLayoutParams(new LinearLayout.LayoutParams(
                (int) width,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));

        TextView textView4 = new TextView(getContext());
        textView4.setText("max.");
        textView4.setTextSize(16);
        textView4.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));

        //EditText Max
        EditText editText2 = new EditText(getContext());
        editText2.setHint("0");
        editText2.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText2.setKeyListener(DigitsKeyListener.getInstance(false, true));
        width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        editText2.setLayoutParams(new LinearLayout.LayoutParams(
                (int) width,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        ));

        TextView textView5 = new TextView(getContext());
        textView5.setText(measure);
        textView5.setTextSize(16);
        textView5.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        ));

// Füge alle Views dem LinearLayout hinzu
        linear.addView(textView1);
        linear.addView(textView2);
        linear.addView(editText);
        linear.addView(textView3);
        linear.addView(space);
        linear.addView(textView4);
        linear.addView(editText2);
        linear.addView(textView5);

        layout.addView(linear);
        linear.setVisibility(View.GONE);
        return linear;
    }
//------------------------------------------------------------------------------------------------------------------

//HashMaps: Creating/Updating-----------------------------------------------------------------------------------------------------------------------

    /**
     * Puts all EditText values and the time picker into their respective HashMaps, preparing them for recipe search.
     * @Author Rene Wentzel
     */
    private void putValuesInHashmap() {
        //Name, Ingredients, Excluded Ingredients
        general.put("query", String.valueOf(name.getText()));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ingredient.getText());
        for (int i = 0; i < ingredientEditList.size(); i++) {
            if (!String.valueOf(ingredientEditList.get(i).getText()).equals("")) {
                stringBuilder.append(",").append(ingredientEditList.get(i).getText());
            }
        }
        general.put("includeIngredients", String.valueOf(stringBuilder));

        stringBuilder = new StringBuilder();
        stringBuilder.append(excludedIngredient.getText());
        for (int i = 0; i < excludeIngredientEditList.size(); i++) {
            if (!String.valueOf(excludeIngredientEditList.get(i).getText()).equals("")) {
                stringBuilder.append(",").append(excludeIngredientEditList.get(i).getText());
            }
        }
        general.put("excludeIngredients", String.valueOf(stringBuilder));

        //Nutrients
        putNutrientToHashmap(macroNutrientList, macronutrients);
        putNutrientToHashmap(microNutrientList, micronutrients);
        putNutrientToHashmap(vitaminList, vitamins);

        //TimePicker
        if(timePicker.getProgress()>0)
            general.put("maxReadyTime", String.valueOf(timePicker.getProgress()));

    }

    /**
     * Filters all min. and max. amount values out of a certain LinearLayout list into a given Hashmap.
     * The given LinearLayouts must be created by createNutrientViews() methode or have to have the same structure.
     * @Author Rene Wentzel
     * @param linear An ArrayList<LinearLayout> with the structure made from createNutrientViews() methode.
     * @param map A Hashmap that will store the filtered amount values.
     */
    private void putNutrientToHashmap(ArrayList<LinearLayout> linear, HashMap<String, String> map) {
        for (int i = 0; i < linear.size(); i++) {
            EditText editMin = (EditText) linear.get(i).getChildAt(2);
            EditText editMax = (EditText) linear.get(i).getChildAt(6);
            String stringMin = String.valueOf(editMin.getText());
            String stringMax = String.valueOf(editMax.getText());
            TextView nameView = (TextView) linear.get(i).getChildAt(0);
            String name = String.valueOf(nameView.getText());
            if (name.contains("Vitamin "))
                name = name.replace("Vitamin ", "");
            String nameMin = "minimum" + name;
            String nameMax = "maximum" + name;
            if (name.equals("Sat. Fat")) {
                nameMin = "minimumSaturatedFat";
                nameMax = "maximumSaturatedFat";
            }
            name = name.replace(" ", "%20");

            Log.d("edit", name);
            Log.d("edit", stringMin);
            Log.d("edit", stringMax);
            if (!stringMin.isEmpty() && !stringMin.equals("0"))
                map.put(nameMin, stringMin);
            if (!stringMax.isEmpty() && !stringMax.equals("0"))
                map.put(nameMax, stringMax);
        }
    }

    /**
     * Creates Hashmaps with items for all values that can be searched for.
     * @Author Rene Wentzel
     */
    private void createSearchHashmaps() {
        general = new HashMap<>();/*
        general.put("query", "");
        general.put("cuisine", "");
        general.put("excludeCuisine", "");
        general.put("diet", "");
        general.put("intolerances", "");
        general.put("includeIngredients", "");
        general.put("excludeIngredients", "");
        general.put("type", "");
        general.put("maxReadyTime", "0");*/

        macronutrients = new HashMap<>();/*
        macronutrients.put("minimumCarbs", "-1.0");
        macronutrients.put("minimumProtein", "-1.0");
        macronutrients.put("minimumCalories", "-1.0");
        macronutrients.put("minimumFat", "-1.0");
        macronutrients.put("minimumSaturatedFat", "-1.0");
        macronutrients.put("minimumFiber", "-1.0");
        macronutrients.put("minimumSugar", "-1.0");
        macronutrients.put("maximumCarbs", "-1.0");
        macronutrients.put("maximumProtein", "-1.0");
        macronutrients.put("maximumCalories", "-1.0");
        macronutrients.put("maximumFat", "-1.0");
        macronutrients.put("maximumSaturatedFat", "-1.0");
        macronutrients.put("maximumFiber", "-1.0");
        macronutrients.put("maximumSugar", "-1.0");*/

        micronutrients = new HashMap<>();/*
        micronutrients.put("minimumCalcium", "-1.0");
        micronutrients.put("minimumCopper", "-1.0");
        micronutrients.put("minimumIron", "-1.0");
        micronutrients.put("minimumMagnesium", "-1.0");
        micronutrients.put("minimumManganese", "-1.0");
        micronutrients.put("minimumPhosphorus", "-1.0");
        micronutrients.put("minimumPotassium", "-1.0");
        micronutrients.put("minimumSelenium", "-1.0");
        micronutrients.put("minimumSodium", "-1.0");
        micronutrients.put("minimumZinc", "-1.0");
        micronutrients.put("minimumCholine", "-1.0");
        micronutrients.put("minimumFolate", "-1.0");
        micronutrients.put("minimumFolicAcid", "-1.0");
        micronutrients.put("minimumIodine", "-1.0");
        micronutrients.put("maximumCalcium", "-1.0");
        micronutrients.put("maximumCopper", "-1.0");
        micronutrients.put("maximumIron", "-1.0");
        micronutrients.put("maximumMagnesium", "-1.0");
        micronutrients.put("maximumManganese", "-1.0");
        micronutrients.put("maximumPhosphorus", "-1.0");
        micronutrients.put("maximumPotassium", "-1.0");
        micronutrients.put("maximumSelenium", "-1.0");
        micronutrients.put("maximumSodium", "-1.0");
        micronutrients.put("maximumZinc", "-1.0");
        micronutrients.put("maximumCholine", "-1.0");
        micronutrients.put("maximumFolate", "-1.0");
        micronutrients.put("maximumFolicAcid", "-1.0");
        micronutrients.put("maximumIodine", "-1.0");*/


        vitamins = new HashMap<>();/*
        vitamins.put("minimumA", "-1.0");
        vitamins.put("minimumC", "-1.0");
        vitamins.put("minimumD", "-1.0");
        vitamins.put("minimumE", "-1.0");
        vitamins.put("minimumK", "-1.0");
        vitamins.put("minimumB1", "-1.0");
        vitamins.put("minimumB2", "-1.0");
        vitamins.put("minimumB3", "-1.0");
        vitamins.put("minimumB5", "-1.0");
        vitamins.put("minimumB6", "-1.0");
        vitamins.put("minimumB12", "-1.0");
        vitamins.put("maximumA", "-1.0");
        vitamins.put("maximumC", "-1.0");
        vitamins.put("maximumD", "-1.0");
        vitamins.put("maximumE", "-1.0");
        vitamins.put("maximumK", "-1.0");
        vitamins.put("maximumB1", "-1.0");
        vitamins.put("maximumB2", "-1.0");
        vitamins.put("maximumB3", "-1.0");
        vitamins.put("maximumB5", "-1.0");
        vitamins.put("maximumB6", "-1.0");
        vitamins.put("maximumB12", "-1.0");*/


    }

    //------------------------------------------------------------------------------------------------------------------
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